package com.chamika.books_project.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Book Management System API",
                description = "API docs for book management system project",
                version = "1.0",
                contact = @Contact(
                        name = "Chamika Jayasinghe",
                        email = "chamika.21@cse.mrt.ac.lk",
                        url = "https://www.linkedin.com/in/chamika-jayasinghe/"
                )
        ),

        servers = {
                @Server(
                        description = "Local Dev Server",
                        url = "http://localhost:8080/api/v1"
                ),

                // TODO: Add production server URL
                @Server(
                        description = "Production Server",
                        url = "aws - url goes here"
                )
        },

        security = {
                @SecurityRequirement(
                        name = "bearerAuth"

                )
        }

)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Token for authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {


}
