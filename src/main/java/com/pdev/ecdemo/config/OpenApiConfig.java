package com.pdev.ecdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("eCommerce API REST Demo")
                        .version("1.0.0")
                        .description("Documentación de la API para la aplicación RESTful demo de comercio electrónico / API documentation for the eCommerce demo RESTful application"))
                .addTagsItem(new Tag().name("Cliente / Client").description("Operaciones específicas para clientes / Specific operations for clients"))
                .addTagsItem(new Tag().name("Orden / Order").description("Operaciones específicas para órdenes / Specific operations for orders"))
                .addTagsItem(new Tag().name("Factura / Invoice").description("Operaciones específicas para facturas / Specific operations for invoices"))
                .addTagsItem(new Tag().name("Producto / Product").description("Operaciones específicas para productos / Specific operations for products"));
    }
}