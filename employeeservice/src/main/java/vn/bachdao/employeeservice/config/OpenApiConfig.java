package vn.bachdao.employeeservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Employee Api Specification - Devtaycode",
                description = "Api documentation for Employee Service",
                version = "1.0",
                contact = @Contact(
                        name = "It zui ze",
                        email = "itzuize@gmail.com",
                        url = "https://..."
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://.../license"
                ),
                termsOfService = "https://.../terms"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:9003"
                ),
                @Server(
                        description = "Dev ENV",
                        url = "https://empoyee-service.dev.com"
                ),
                @Server(
                        description = "Prod ENV",
                        url = "https://empoyee-service.prod.com"
                )

        }
)
public class OpenApiConfig {
}
