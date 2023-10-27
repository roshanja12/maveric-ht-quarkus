package org.maveric.quarkus.panache.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@ApplicationPath("/")
@OpenAPIDefinition(
        info = @Info(title = "Saving Account Api",
                description = "Manage Saving Accounts and Transactions Operations",
                version = "1.0",
                contact = @Contact(name = "@mock",url = "https://mock")),
        externalDocs = @ExternalDocumentation( description= "All saving account api code mention below",url = "https://mock"),
        tags= {
                @Tag(name = "Saving Account Api", description = "Public API"),
                @Tag(name = "Saving Account Api", description = "Public API")
        }
)
public class swaggerConfig extends Application {
}

