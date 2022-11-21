package com.company.orderms.service.impl;

import com.company.orderms.dto.OrderDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueueListener {
    public static final String QUEUE = "queue_1";
    public static final String EXCHANGE = "exchange_1";
    public static final String ROUTING_KEY = "routing_key_1";

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    public void insertOrder(OrderDto dto) {
//        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, dto);
//    }
////
    @RabbitListener(queues = QUEUE)
    public OrderDto getOrder(OrderDto orderDto) {
        System.out.println("Message received from queue: " + orderDto);
        return orderDto;
    }
}
