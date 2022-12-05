package com.company.userms.client;

import com.company.userms.client.dto.CreateOrderDto;
import com.company.userms.client.dto.OrderDto;
import com.company.userms.client.dto.OrderListDto;
import com.company.userms.client.dto.UserOrderParams;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(value = "OrderClient", url = "http://localhost:8888/api/order/public/v1/orders")
public interface OrderClient {
    @PostMapping
    OrderDto createOrder(@RequestBody CreateOrderDto dto);

    @GetMapping("/{orderId}")
    OrderDto getOrderById(@PathVariable UUID orderId);

    @GetMapping
    OrderListDto getOrders(@SpringQueryMap UserOrderParams queryParameters);
}

