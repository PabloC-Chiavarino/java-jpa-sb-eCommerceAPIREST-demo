package com.pdev.ecdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("eCommerce API REST Demo")
                        .version("1.0.0")
                        .description("Documentación de la API para la aplicación RESTful demo de comercio electrónico / API documentation for the eCommerce demo RESTful application"));
    }
}
