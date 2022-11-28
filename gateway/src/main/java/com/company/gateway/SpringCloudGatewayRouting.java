package com.company.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringCloudGatewayRouting {
    private final AuthGatewayFilter authGatewayFilter;

    @Bean
    public RouteLocator configureRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/admin/**").filters(f ->
                                f.rewritePath("/service(?<segment>/?.*)", "$\\{segment}")
                                        .filter(authGatewayFilter.setRole("ROLE_ADMIN")))
                        .uri("lb://ADMIN-MS"))
                .route(r -> (r.path("/api/order/**").filters(f ->
                        f.rewritePath("/service(?<segment>/?.*)", "$\\{segment}")
                                .filter(authGatewayFilter)))
                        .uri("lb://ORDER-MS"))
                .route(r -> r.path("/api/user/**").filters(f ->
                        f.rewritePath("/service(?<segment>/?.*)", "$\\{segment}")
                                .filter(authGatewayFilter))
                        .uri("lb://USER-MS"))
                .route(r -> r.path("/api/auth/**").filters(f ->
                        f.rewritePath("/service(?<segment>/?.*)", "$\\{segment}")
                                .filter(authGatewayFilter))
                        .uri("lb://AUTH-MS"))
                .route(r -> r.path("/api/courier/**").filters(f ->
                        f.rewritePath("/service(?<segment>/?.*)", "$\\{segment}")
                                .filter(authGatewayFilter.setRole("ROLE_COURIER")))
                        .uri("lb://COURIER-MS"))
                .build();
    }
}