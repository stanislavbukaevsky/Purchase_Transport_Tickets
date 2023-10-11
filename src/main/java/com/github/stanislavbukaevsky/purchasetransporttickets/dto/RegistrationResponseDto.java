package com.github.stanislavbukaevsky.purchasetransporttickets.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Класс-DTO для ответа пользователю с его полной информацией
 */
@Data
@Schema(description = "Объект регистрации для ответа пользователю")
public class RegistrationResponseDto {
    @Schema(description = "Уникальный идентификатор пользователя")
    private Long id;
    @Schema(description = "Логин пользователя при регистрации, аутентификации и авторизации")
    private String login;
    @Schema(description = "Пароль пользователя")
    private String password;
    @Schema(description = "Имя пользователя")
    private String firstName;
    @Schema(description = "Отчество пользователя")
    private String middleName;
    @Schema(description = "Фамилия пользователя")
    private String lastName;
    @Schema(description = "Роль пользователя")
    private String role;
}
