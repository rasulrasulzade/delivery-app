package com.company.orderms.service.impl;

import com.company.orderms.dto.OrderQueueDto;
import com.company.orderms.dto.UpdateDtoRequest;
import com.company.orderms.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueConsumer {
    private final ObjectMapper objectMapper;

    private final OrderService orderService;
    public static final String QUEUE = "order_queue";
    public static final String EXCHANGE = "order_exchange";
    public static final String ROUTING_KEY = "order_key";

    private OrderQueueDto parseDto(String str) {
        OrderQueueDto dto = null;
        try {
            dto = objectMapper.readValue(str, OrderQueueDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return dto;
    }

    @RabbitListener(queues = QUEUE)
    public void getOrder(String str) {
        OrderQueueDto dto = parseDto(str);
        UpdateDtoRequest updateDtoRequest = UpdateDtoRequest.builder()
                .status(dto.getStatus())
                .courierId(dto.getCourierId())
                .destination(dto.getDestination())
                .build();
        orderService.updateOrder(dto.getId(), updateDtoRequest);
    }
}
