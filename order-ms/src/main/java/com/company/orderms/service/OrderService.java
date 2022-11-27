package com.company.orderms.service;

import com.company.orderms.dto.CreateOrderDto;
import com.company.orderms.dto.OrderDto;
import com.company.orderms.dto.OrderListDto;
import com.company.orderms.dto.UpdateDtoRequest;

import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    OrderDto createOrder(CreateOrderDto dto);

    OrderDto getOrderById(UUID id);

    void updateOrder(UUID id, UpdateDtoRequest request);

    OrderListDto getOrders(Optional<UUID> userId, Optional<UUID> courierId, Optional<Integer> page, Optional<Integer> pageSize, Optional<String> direction, Optional<String> sortBy);
}
