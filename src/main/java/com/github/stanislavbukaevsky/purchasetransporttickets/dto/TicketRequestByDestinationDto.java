package com.github.stanislavbukaevsky.purchasetransporttickets.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Класс-DTO для запроса от пользователя информации о пункте назначения
 */
@Data
@Schema(description = "Объект пункта назначения для запроса от пользователя")
public class TicketRequestByDestinationDto {
    @NotEmpty(message = "Поле пункта назначения не должно быть пустым!")
    @Size(min = 2, max = 64, message = "Пункт назначения должен содержать от 2 до 64 символов!")
    @Schema(description = "Пункт назначения покупателя")
    private String destination;
}
