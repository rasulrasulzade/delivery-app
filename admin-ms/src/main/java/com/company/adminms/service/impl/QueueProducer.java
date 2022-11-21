package com.company.adminms.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueueProducer {
    public static final String QUEUE = "order_queue";
    public static final String EXCHANGE = "order_exchange";
    public static final String ROUTING_KEY = "order_key";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void insertOrder(String text) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, text);
    }
}
