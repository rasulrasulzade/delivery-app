package com.company.userms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("PUBLIC")
                .pathsToMatch("/public/**")
                .addOperationCustomizer(publicOperationCustomizer())
                .build();
    }

    public OperationCustomizer publicOperationCustomizer() {
        return (operation, handlerMethod) -> operation.addParametersItem(
                new Parameter()
                        .in("header")
                        .required(true)
                        .description("Authorization token")
                        .name("Authorization"));
    }

    @Bean
    public GroupedOpenApi defaultApi() {
        return GroupedOpenApi.builder()
                .group("DEFAULT")
                .pathsToMatch("/healthcheck")
                .build();
    }

    @Bean
    public OpenAPI getOpenApi() {
        return new OpenAPI().info(apiInfo());
    }

    private Info apiInfo() {
        return new Info().title("User ms")
                .description("Live Code System Swagger Open API")
                .version("v1.0.0");
    }
}

