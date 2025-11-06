package uz.lb.updaterserver.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Roles and Permissions API")
                        .version("3.1.0")
                        .description("RBAC (Role-Based Access Control) tizimi uchun API hujjatlar"));
    }
}
