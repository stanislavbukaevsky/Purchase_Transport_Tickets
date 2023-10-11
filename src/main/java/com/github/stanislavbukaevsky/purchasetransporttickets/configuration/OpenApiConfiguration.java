package com.github.stanislavbukaevsky.purchasetransporttickets.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурационный класс для настройки и аутентификации в Swagger
 */
@Configuration
@OpenAPIDefinition(info =
@Info(title = "Приложение для покупки транспортных билетов", description = "Web интерфейс для покупки транспортных билетов", contact =
@Contact(name = "Букаевский Станислав", email = "stanislavbukaevsky@yandex.ru"))
)
@SecurityScheme(name = "JWT", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class OpenApiConfiguration {

}
