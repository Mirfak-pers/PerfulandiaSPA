package com.example.PerfulandiaSpa.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customAPI(){
        return new OpenAPI().info(new Info().title("PerfulandiaSPA").version("1.0").description("Primera fase de la APi"));
    }


}
