package com.company.adminms.service;

import com.company.adminms.dto.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;

public interface AdminService {
    CourierListDto getCouriers();

    OrderDto getOrderByOrderId(UUID orderId);

    OrderListDto getOrders(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> pageSize, @RequestParam Optional<String> direction, @RequestParam Optional<String> sortBy);

    void updateOrder(UUID orderId, UpdateOrderDto dto);
}
