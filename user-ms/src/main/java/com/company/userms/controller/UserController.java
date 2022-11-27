package com.company.userms.controller;

import com.company.userms.client.dto.LocationDto;
import com.company.userms.client.dto.OrderDto;
import com.company.userms.client.dto.OrderListDto;
import com.company.userms.dto.CreateOrderDto;
import com.company.userms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/{userId}/orders")
    public OrderDto createUserOrder(@PathVariable("userId") UUID userId, @RequestBody CreateOrderDto createOrderDto) {
        return userService.createUserOrder(userId, createOrderDto);
    }

    @GetMapping("/{userId}/orders")
    public OrderListDto getUserOrders(@PathVariable UUID userId, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> pageSize, @RequestParam Optional<String> direction, @RequestParam Optional<String> sortBy) {
        return userService.getUserOrders(userId, page, pageSize, direction, sortBy);
    }

    @GetMapping("/orders/{orderId}")
    public OrderDto createUserOrder(@PathVariable UUID orderId) {
        return userService.getOrderByOrderId(orderId);
    }


    @PutMapping("/orders/{orderId}/destination")
    public void changeDestination(@PathVariable UUID orderId, @RequestBody LocationDto destination) {
        userService.changeDestination(orderId, destination);
    }

    @PutMapping("/orders/{orderId}/cancel")
    public void changeOrder(@PathVariable UUID orderId) {
        userService.cancelDestination(orderId);
    }
}
