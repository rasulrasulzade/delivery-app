package com.company.adminms.controller;

import com.company.adminms.dto.CourierListDto;
import com.company.adminms.dto.OrderDto;
import com.company.adminms.dto.OrderListDto;
import com.company.adminms.dto.UpdateOrderDto;
import com.company.adminms.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/couriers")
    public CourierListDto getCouriers() {
        return adminService.getCouriers();
    }

    @GetMapping("/orders")
    public OrderListDto getUserOrders(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> pageSize, @RequestParam Optional<String> direction, @RequestParam Optional<String> sortBy) {
        return adminService.getOrders(page, pageSize, direction, sortBy);
    }

    @GetMapping("/orders/{orderId}")
    public OrderDto getUserOrder(@PathVariable UUID orderId) {
        return adminService.getOrderByOrderId(orderId);
    }

    @PutMapping("/orders/{orderId}")
    public void changeOrder(@PathVariable UUID orderId, @RequestBody UpdateOrderDto dto) {
        adminService.updateOrder(orderId, dto);
    }
}
