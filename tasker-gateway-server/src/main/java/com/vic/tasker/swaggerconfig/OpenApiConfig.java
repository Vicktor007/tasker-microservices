package com.vic.tasker.swaggerconfig;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tasker microservices backend API service")
                        .version("0.0.1-SNAPSHOT")
                        .description("Tasker backend services")
                        .contact(new Contact()
                                .name("Victor Olayiwola")
                                .email("vicktord007@gmail.com")));
    }
}
