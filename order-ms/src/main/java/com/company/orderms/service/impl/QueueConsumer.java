package com.company.orderms.service.impl;

import com.company.orderms.dto.OrderDto;
import com.company.orderms.dto.QueueOrderDto;
import com.company.orderms.dto.UpdateDtoRequest;
import com.company.orderms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueConsumer {
    private final OrderService orderService;
    public static final String QUEUE = "order_queue";
    public static final String EXCHANGE = "order_exchange";
    public static final String ROUTING_KEY = "order_key";

    @RabbitListener(queues = QUEUE)
    public OrderDto getOrder(QueueOrderDto dto) {
        System.out.println("Message received from queue: " + dto);
        UpdateDtoRequest updateDtoRequest = UpdateDtoRequest.builder()
                .status(dto.getStatus()).courierId(dto.getCourierId()).build();
        return orderService.updateOrder(dto.getId(), updateDtoRequest);
    }
}
