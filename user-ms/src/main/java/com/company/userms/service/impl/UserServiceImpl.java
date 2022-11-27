package com.company.userms.service.impl;

import com.company.userms.client.OrderClient;
import com.company.userms.client.dto.*;
import com.company.userms.client.enums.OrderStatus;
import com.company.userms.dto.CreateOrderDto;
import com.company.userms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final OrderClient orderClient;
    private final OrderQueueProducer orderQueueProducer;

    @Override
    public OrderDto createUserOrder(UUID userId, CreateOrderDto createOrderDto) {
        com.company.userms.client.dto.CreateOrderDto clientDto = com.company.userms.client.dto.CreateOrderDto.builder()
                .userId(userId)
                .comment(createOrderDto.getComment())
                .pickup(createOrderDto.getPickup())
                .destination(createOrderDto.getDestination())
                .build();
        return orderClient.createOrder(clientDto);
    }

    @Override
    public OrderDto getOrderByOrderId(UUID orderId) {
        return orderClient.getOrderById(orderId);
    }

    @Override
    public OrderListDto getUserOrders(UUID userId, Optional<Integer> page, Optional<Integer> pageSize, Optional<String> direction, Optional<String> sortBy) {
        UserOrderParams params = new UserOrderParams();
        params.setUserId(userId);
        params.setPage(page.orElse(null));
        params.setPageSize(pageSize.orElse(null));
        params.setDirection(direction.orElse(null));
        params.setSortBy(sortBy.orElse(null));
        return orderClient.getOrders(params);
    }

    @Override
    public void changeDestination(UUID orderId, LocationDto destination) {
        OrderQueueDto orderQueueDto = OrderQueueDto.builder()
                .id(orderId)
                .destination(destination)
                .build();
        orderQueueProducer.insertOrder(orderQueueDto);
    }

    @Override
    public void cancelDestination(UUID orderId) {
        OrderQueueDto orderQueueDto = OrderQueueDto.builder()
                .id(orderId)
                .status(OrderStatus.CANCELED)
                .build();
        orderQueueProducer.insertOrder(orderQueueDto);
    }
}
