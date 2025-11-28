package space.bielsolososdev.rdl.core.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    @ConditionalOnProperty(name = "rdl.swagger-enabled", havingValue = "true", matchIfMissing = true)
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Redirect Lab API")
                        .version("1.0.0")
                        .description("API para gerenciamento de URLs encurtadas e redirects")
                        .contact(new Contact()
                                .name("Biel Soloso")
                                .url("https://github.com/bielsolosos")
                                .email("contato@bielsolososdev.space"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Servidor de Desenvolvimento"))
                .addServersItem(new Server()
                        .url("https://rdl.bielsolososdev.space")
                        .description("Servidor de Produção"))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Insira o token JWT no formato: Bearer {token}")));
    }
}
