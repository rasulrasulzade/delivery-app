package com.company.orderms.controller;

import com.company.orderms.dto.CreateOrderDto;
import com.company.orderms.dto.OrderDto;
import com.company.orderms.dto.OrderListDto;
import com.company.orderms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderDto dto) {
        return new ResponseEntity<>(orderService.createOrder(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable UUID id) {
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<OrderListDto> getOrders(@RequestParam Optional<UUID> userId, @RequestParam Optional<UUID> courierId, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> pageSize, @RequestParam Optional<String> direction, @RequestParam Optional<String> sortBy) {
        OrderListDto dtoList = orderService.getOrders(userId, courierId, page, pageSize, direction, sortBy);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}
