package com.company.userms.service;

import com.company.userms.client.dto.*;
import com.company.userms.dto.CreateOrderDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    OrderDto createUserOrder(UUID userId, CreateOrderDto createOrderDto);

    OrderDto getOrderByOrderId(UUID orderId);

    OrderListDto getUserOrders(@RequestParam UUID userId, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> pageSize, @RequestParam Optional<String> direction, @RequestParam Optional<String> sortBy);

    void changeDestination(UUID orderId, LocationDto destination);

    void cancelDestination(UUID orderId);
}
