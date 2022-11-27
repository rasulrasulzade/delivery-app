package com.company.courierms.service.impl;

import com.company.courierms.dto.OrderQueueDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderQueueProducer {
    private final ObjectMapper objectMapper;
    public static final String EXCHANGE = "order_exchange";
    public static final String ROUTING_KEY = "order_key";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private String parseString(OrderQueueDto dto) {
        String str = null;
        try {
            str = objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return str;
    }
    public void insertOrder(OrderQueueDto dto) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, parseString(dto));
    }
}