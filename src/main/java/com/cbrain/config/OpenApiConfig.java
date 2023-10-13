package com.cbrain.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Student Manager",
                version = "1.0",
                description = "API for managing student records",
                contact = @Contact(name = "DSP Chathuranga", email = "dspchathuranga@gmail.com", url = "https://github.com/dspchathuranga"),
                termsOfService = "https://github.com/dspchathuranga/student-manager"
        ),
        servers = {
                @Server(
                        description = "Dev ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Prod ENV",
                        url = "http://localhost:8081"
                )
        }
)
public class OpenApiConfig {
}
