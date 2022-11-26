package com.company.authms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI getOpenApi() {
        return new OpenAPI().info(apiInfo());
    }

    private Info apiInfo() {
        return new Info().title("Auth ms")
                .description("Live Code System Swagger Open API")
                .version("v1.0.0");
    }
}
