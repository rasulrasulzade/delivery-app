package com.company.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringCloudGatewayRouting {
    private final AuthFilterFactory authFilter;

    @Bean
    public RouteLocator configureRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/admin/**").filters(f ->
                                f.rewritePath("/service(?<segment>/?.*)", "$\\{segment}")
                                        .filter(authFilter.apply(authFilter.newConfig().setRole("ROLE_ADMIN"))))
                        .uri("lb://ADMIN-MS"))
                .route(r -> (r.path("/api/order/**").filters(f ->
                        f.rewritePath("/service(?<segment>/?.*)", "$\\{segment}")
                                .filter(authFilter.apply(authFilter.newConfig().setRole("ROLE_USER")))))
                        .uri("lb://ORDER-MS"))
                .route(r -> r.path("/api/user/**").filters(f ->
                        f.rewritePath("/service(?<segment>/?.*)", "$\\{segment}")
                                .filter(authFilter.apply(authFilter.newConfig().setRole("ROLE_USER"))))
                        .uri("lb://USER-MS"))
                .route(r -> r.path("/api/auth/**").uri("lb://AUTH-MS"))
                .route(r -> r.path("/api/courier/**").filters(f ->
                        f.rewritePath("/service(?<segment>/?.*)", "$\\{segment}")
                                .filter(authFilter.apply(authFilter.newConfig().setRole("ROLE_COURIER"))))
                        .uri("lb://COURIER-MS"))
                .build();
    }
}