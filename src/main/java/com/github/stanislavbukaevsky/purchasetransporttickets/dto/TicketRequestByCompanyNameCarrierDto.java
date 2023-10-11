package com.github.stanislavbukaevsky.purchasetransporttickets.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Класс-DTO для запроса от пользователя информации о названии транспортной компании
 */
@Data
@Schema(description = "Объект названия транспортной компании для запроса от пользователя")
public class TicketRequestByCompanyNameCarrierDto {
    @NotEmpty(message = "Поле название компании перевозчика не должно быть пустым!")
    @Size(min = 2, max = 48, message = "Название компании перевозчика должно содержать от 2 до 48 символов!")
    @Schema(description = "Название компании перевозчика")
    private String companyName;
}
