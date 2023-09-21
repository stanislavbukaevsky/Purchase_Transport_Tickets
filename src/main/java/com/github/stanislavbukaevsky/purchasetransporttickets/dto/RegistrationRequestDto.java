package com.github.stanislavbukaevsky.purchasetransporttickets.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequestDto {
    @NotEmpty(message = "Поле логина не должно быть пустым!")
    @Size(min = 2, max = 16, message = "Имя пользователя должно содержать от 2 до 16 символов!")
    @Schema(description = "Логин пользователя при регистрации, аутентификации и авторизации")
    private String login;
    @NotEmpty(message = "Поле пароля не должно быть пустым!")
    @Schema(description = "Пароль пользователя")
    private String password;
    @NotEmpty(message = "Поле имени не должно быть пустым!")
    @Size(min = 2, max = 20, message = "Имя должно содержать от 2 до 20 символов!")
    @Schema(description = "Имя пользователя")
    private String firstName;
    @NotEmpty(message = "Поле отчества не должно быть пустым!")
    @Size(min = 2, max = 20, message = "Отчество должно содержать от 2 до 20 символов!")
    @Schema(description = "Отчество пользователя")
    private String middleName;
    @NotEmpty(message = "Поле фамилии не должно быть пустым!")
    @Size(min = 2, max = 36, message = "Фамилия должна содержать от 2 до 36 символов!")
    @Schema(description = "Фамилия пользователя")
    private String lastName;
}
