package com.company.adminms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final WebClient.Builder webClientBuilder;

    @GetMapping("/test")
    public String test(){
        Integer i =  webClientBuilder.build()
                .get()
                .uri("http://localhost:8888/api/order/healthcheck")
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
        return "RESPONSE FROM order: " + 1;
    }
}
