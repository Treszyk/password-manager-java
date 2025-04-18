package com.treszyk.passwordmanager.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
        @Bean
        public OpenAPI customOpenAPI() {
                final String securitySchemeName = "bearerAuth";
                return new OpenAPI()
                        .info(new Info()
                                .title("Password Manager API")
                                .version("v1")
                                .description("Login to get a token and use protected endpoints")
                        )
                        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                        .components(new Components().addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        ));
        }
}
