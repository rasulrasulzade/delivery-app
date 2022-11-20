package com.company.adminms.service;

import com.company.adminms.dto.OrderDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    public static final String QUEUE = "queue_1";

    @RabbitListener(queues = QUEUE)
    public void getOrder(OrderDto orderDto) {
        System.out.println("Message received from queue: " + orderDto);
    }
}
