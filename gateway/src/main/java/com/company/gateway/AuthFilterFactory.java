package com.company.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthFilterFactory extends AbstractGatewayFilterFactory<AuthFilterFactory.Config> {
    @Value("${token.secret}")
    private String secret;

    public AuthFilterFactory() {
        super(Config.class);
    }

    public static class Config {
        private String role;

        public Config setRole(String role) {
            this.role = role;
            return this;
        }

        public String getRole() {
            return this.role;
        }
    }

    @Override
    public Config newConfig() {
        return new Config();
    }


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization error", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "");

            if (!isJwtValid(jwt)) {
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }

            if(!getRolesFromToken(jwt).contains(config.getRole())){
                return onError(exchange, "No permission", HttpStatus.FORBIDDEN);
            }
            return chain.filter(exchange);
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);

        return response.setComplete();
    }

    private Jws<Claims> getAllClaimsFromToken(String jwt) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt);
    }

    private String getRolesFromToken(String jwt) {
        return getAllClaimsFromToken(jwt).getBody().get("roles").toString();
    }

    private boolean isJwtValid(String jwt) {
        boolean value = true;
        try {
            getAllClaimsFromToken(jwt);
        } catch (Exception ex) {
            value = false;
        }
        return value;
    }
}
