package com.company.courierms.service.impl;

import com.company.courierms.client.OrderClient;
import com.company.courierms.dto.CourierOrderParams;
import com.company.courierms.dto.OrderDto;
import com.company.courierms.dto.OrderListDto;
import com.company.courierms.dto.OrderQueueDto;
import com.company.courierms.enums.OrderStatus;
import com.company.courierms.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {
    private final OrderClient orderClient;
    private final OrderQueueProducer orderQueueProducer;

    @Override
    public OrderDto getOrderByOrderId(UUID orderId) {
        return orderClient.getOrderById(orderId);
    }

    @Override
    public OrderListDto getCourierOrders(UUID courierId, Optional<Integer> page, Optional<Integer> pageSize, Optional<String> direction, Optional<String> sortBy) {
        CourierOrderParams params = new CourierOrderParams();
        params.setCourierId(courierId);
        params.setPage(page.orElse(null));
        params.setPageSize(pageSize.orElse(null));
        params.setDirection(direction.orElse(null));
        params.setSortBy(sortBy.orElse(null));
        return orderClient.getOrders(params);
    }

    @Override
    public void changeOrderStatus(UUID orderId, OrderStatus orderStatus) {
        OrderQueueDto orderQueueDto = OrderQueueDto.builder()
                .id(orderId)
                .status(orderStatus)
                .build();
        orderQueueProducer.insertOrder(orderQueueDto);
    }
}
