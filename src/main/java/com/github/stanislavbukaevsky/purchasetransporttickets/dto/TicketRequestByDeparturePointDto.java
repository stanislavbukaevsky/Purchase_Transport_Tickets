package com.github.stanislavbukaevsky.purchasetransporttickets.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Класс-DTO для запроса от пользователя информации о пункте отправления
 */
@Data
@Schema(description = "Объект пункта отправления для запроса от пользователя")
public class TicketRequestByDeparturePointDto {
    @NotEmpty(message = "Поле пункта отправления не должно быть пустым!")
    @Size(min = 2, max = 64, message = "Пункт отправления должен содержать от 2 до 64 символов!")
    @Schema(description = "Пункт отправления покупателя")
    private String departurePoint;
}
