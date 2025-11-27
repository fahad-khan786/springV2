package com.example.demo.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server().url("http://192.168.2.95:9988") // <-- Replace with your IP
                ))
                .info(new Info()
                        .title("Spring Boot Student API")
                        .version("1.0")
                        .description("Exposed for AEM integration"));
    }

}
