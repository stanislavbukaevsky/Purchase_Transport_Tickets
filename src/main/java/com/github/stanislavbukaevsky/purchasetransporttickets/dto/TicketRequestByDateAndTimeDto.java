package com.github.stanislavbukaevsky.purchasetransporttickets.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Класс-DTO для запроса от пользователя информации о дате и времени отправления
 */
@Data
@Schema(description = "Объект даты и времени отправления для запроса от пользователя")
public class TicketRequestByDateAndTimeDto {
    @NotEmpty(message = "Поле даты и времени отправления не должно быть пустым!")
    @Size(min = 16, max = 20, message = "Дата и время отправления должно содержать от 16 до 20 символов! Дата и время отправления должны быть в следующим формате: \"yyyy-MM-dd HH:mm\"")
    @Schema(description = "Дата и время отправления")
    private String dateTimeRequest;
}
