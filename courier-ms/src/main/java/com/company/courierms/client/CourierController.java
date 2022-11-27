package com.company.courierms.client;

import com.company.courierms.dto.ChangeOrderStatusDto;
import com.company.courierms.dto.OrderDto;
import com.company.courierms.dto.OrderListDto;
import com.company.courierms.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/couriers")
public class CourierController {
    private final CourierService courierService;

    @GetMapping("/{courierId}/orders")
    public OrderListDto getCourierOrders(@PathVariable UUID courierId, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> pageSize, @RequestParam Optional<String> direction, @RequestParam Optional<String> sortBy) {
        return courierService.getCourierOrders(courierId, page, pageSize, direction, sortBy);
    }

    @GetMapping("/orders/{orderId}")
    public OrderDto createUserOrder(@PathVariable UUID orderId) {
        return courierService.getOrderByOrderId(orderId);
    }

    @PutMapping("/orders/{orderId}/status")
    public void changeOrderStatus(@PathVariable UUID orderId, @RequestBody ChangeOrderStatusDto dto) {
        courierService.changeOrderStatus(orderId, dto.getOrderStatus());
    }
}
