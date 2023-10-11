package com.github.stanislavbukaevsky.purchasetransporttickets.dto;

import com.github.stanislavbukaevsky.purchasetransporttickets.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс-DTO для ответа с информацией об аутентифицированном пользователе
 */
@Data
@Schema(description = "Объект аутентификации для ответа пользователю")
public class InfoAuthenticationUserDto {
    @Schema(description = "Уникальный идентификатор пользователя")
    private Long userId;
    @Schema(description = "Логин пользователя")
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
    private Role role;
    @Schema(description = "Access токен пользователя")
    private String accessToken;
    @Schema(description = "Refresh токен пользователя")
    private String refreshToken;
    @Schema(description = "Время истечения срока действия access токена")
    private LocalDateTime expiresAtAccess;
    @Schema(description = "Время истечения срока действия refresh токена")
    private LocalDateTime expiresAtRefresh;
}
