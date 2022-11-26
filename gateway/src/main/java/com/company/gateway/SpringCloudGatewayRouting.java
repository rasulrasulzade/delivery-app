package com.company.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudGatewayRouting {

    @Bean
    public RouteLocator configureRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/admin/**").uri("lb://ADMIN-MS"))
                .route(r -> r.path("/api/order/**").uri("lb://ORDER-MS"))
                .route(r -> r.path("/api/user/**").uri("lb://USER-MS"))
                .route(r -> r.path("/api/auth/**").uri("lb://AUTH-MS"))
                .build();
    }
}