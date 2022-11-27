package com.company.courierms.client;

import com.company.courierms.dto.CourierOrderParams;
import com.company.courierms.dto.OrderDto;
import com.company.courierms.dto.OrderListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(value = "OrderClient", url = "http://localhost:8888/api/order/v1/orders")
public interface OrderClient {

    @GetMapping("/{orderId}")
    OrderDto getOrderById(@PathVariable UUID orderId);

    @GetMapping
    OrderListDto getOrders(@SpringQueryMap CourierOrderParams queryParameters);
}