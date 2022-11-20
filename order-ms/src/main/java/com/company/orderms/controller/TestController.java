package com.company.orderms.controller;

import com.company.orderms.dto.OrderDto;
import com.company.orderms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final OrderService orderService;

    @PostMapping("/queue")
    public void insertDataToQueue(@RequestBody OrderDto dto) {
        orderService.insertOrder(dto);
    }
}
