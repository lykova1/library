package library.library_collection.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Класс конфигурации для документации OpenAPI.
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Library Collection Api",
                description = "Library Collection", version = "1.0.0",
                contact = @Contact(
                        name = "Lykova Ekaterina",
                        email = "e.lykova03@gmail.com"
                )
        )
)
public class OpenApiConfig {
}
