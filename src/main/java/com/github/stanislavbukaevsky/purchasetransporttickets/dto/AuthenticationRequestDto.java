package com.github.stanislavbukaevsky.purchasetransporttickets.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Класс-DTO для запроса от пользователя на аутентификацию
 */
@Data
@Schema(description = "Объект для аутентификации пользователя")
public class AuthenticationRequestDto {
    @NotEmpty(message = "Поле логина не должно быть пустым!")
    @Size(min = 2, max = 16, message = "Логин должен содержать от 2 до 16 символов!")
    @Schema(description = "Логин пользователя")
    private String login;
    @NotEmpty(message = "Поле пароля не должно быть пустым!")
    @Schema(description = "Пароль пользователя")
    private String password;
}
