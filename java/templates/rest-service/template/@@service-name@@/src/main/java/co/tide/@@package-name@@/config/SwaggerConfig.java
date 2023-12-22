package co.tide.@@package-name@@.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for all(/some) of your Swagger needs. Your generated documentation will live at
 * https://@@service-name@@.rest.wip.internal.tide.co/swagger-ui/index.html
 * or
 * http://localhost:8080/swagger-ui/index.html when running locally.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(
        @Value("${server.servlet.context-path}") String contextPath) {
        return new OpenAPI()
                .components(new Components()
                .addSecuritySchemes("bearer-key",
                        new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                                .bearerFormat("JWT")))
                .info(new Info()
                        .title("@@service-name@@")
                        .description("The @@service-name@@ is responsible for doing exciting things!")
                        .version("1.0"))
                .addServersItem(new Server().url(contextPath).description("Service context path"));
    }

}