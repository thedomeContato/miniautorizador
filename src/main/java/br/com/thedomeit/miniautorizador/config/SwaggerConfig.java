package br.com.thedomeit.miniautorizador.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {

        return new OpenAPI()
                .info(new Info().title("MiniAutorizador - Desafio VR")
                        .description("Autorizar Manutenções relativas ao VR Card e pagamentos com cartão.")
                        .version("v0.0.1"));
    }
}