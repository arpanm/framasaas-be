package com.framasaas.be.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
            .info(new Info()
                .title("Framasaas API")
                .description("API documentation for Framasaas application")
                .version("0.0.1"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("public")
            .pathsToMatch("/api/**")
            .build();
    }

    @Bean
    public GroupedOpenApi managementApi() {
        return GroupedOpenApi.builder()
            .group("management")
            .pathsToMatch("/management/**")
            .build();
    }
}