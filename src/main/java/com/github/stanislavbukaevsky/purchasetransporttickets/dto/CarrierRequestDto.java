package com.github.stanislavbukaevsky.purchasetransporttickets.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Класс-DTO для запроса от пользователя на информацию о транспортном перевозчике
 */
@Data
@Schema(description = "Объект перевозчика для запроса от пользователя")
public class CarrierRequestDto {
    @NotEmpty(message = "Поле название компании перевозчика не должно быть пустым!")
    @Size(min = 2, max = 48, message = "Название компании перевозчика должно содержать от 2 до 48 символов!")
    @Schema(description = "Название компании перевозчика")
    private String companyName;
    @NotEmpty(message = "Поле номера телефона компании перевозчика не должно быть пустым!")
    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "Вы ввели недопустимый номер телефона!")
    @Schema(description = "Номер телефона компании перевозчика")
    private String phoneNumber;
}
