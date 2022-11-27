package com.company.courierms.service;

import com.company.courierms.dto.OrderDto;
import com.company.courierms.dto.OrderListDto;
import com.company.courierms.enums.OrderStatus;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;

public interface CourierService {
    OrderDto getOrderByOrderId(UUID orderId);

    OrderListDto getCourierOrders(@RequestParam UUID courierId, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> pageSize, @RequestParam Optional<String> direction, @RequestParam Optional<String> sortBy);

    void changeOrderStatus(UUID orderId, OrderStatus orderStatus);
}
