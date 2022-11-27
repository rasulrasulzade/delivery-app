package com.company.adminms.service.impl;

import com.company.adminms.client.AuthClient;
import com.company.adminms.client.OrderClient;
import com.company.adminms.dto.*;
import com.company.adminms.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AuthClient authClient;
    private final OrderClient orderClient;

    private final OrderQueueProducer orderQueueProducer;

    @Override
    public CourierListDto getCouriers() {
        return authClient.getCouriers();
    }

    @Override
    public OrderDto getOrderByOrderId(UUID orderId) {
        return orderClient.getOrderById(orderId);
    }

    @Override
    public OrderListDto getOrders(Optional<Integer> page, Optional<Integer> pageSize, Optional<String> direction, Optional<String> sortBy) {
        AdminParams params = new AdminParams();
        params.setPage(page.orElse(null));
        params.setPageSize(pageSize.orElse(null));
        params.setDirection(direction.orElse(null));
        params.setSortBy(sortBy.orElse(null));
        return orderClient.getOrders(params);
    }

    @Override
    public void updateOrder(UUID orderId, UpdateOrderDto dto) {
        OrderQueueDto orderQueueDto = OrderQueueDto.builder()
                .id(orderId)
                .courierId(dto.getCourierId())
                .status(dto.getStatus())
                .build();
        orderQueueProducer.insertOrder(orderQueueDto);
    }
}
