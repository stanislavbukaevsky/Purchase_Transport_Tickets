package com.github.stanislavbukaevsky.purchasetransporttickets.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Класс-DTO для запроса от пользователя на информацию о транспортном билете
 */
@Data
@Schema(description = "Объект транспортного билета для запроса от пользователя")
public class TicketRequestDto {
    @NotNull(message = "Поле идентификатора маршрута не должно быть пустым!")
    @Positive(message = "Поле идентификатора маршрута должно содержать только положительные цифры")
    @Schema(description = "Уникальный идентификатор номера маршрута")
    private Long routeId;
    @NotEmpty(message = "Поле даты и времени отправления не должно быть пустым!")
    @Size(min = 16, max = 20, message = "Дата и время отправления должно содержать от 16 до 20 символов! Дата и время отправления должны быть в следующим формате: \"yyyy-MM-dd HH:mm\"")
    @Schema(description = "Дата и время отправления")
    private String dateTimeDeparture;
    @NotNull(message = "Поле посадочного места не должно быть пустым!")
    @Positive(message = "Поле посадочного места должно содержать только положительные цифры")
    @Schema(description = "Посадочное места")
    private Integer seatNumber;
    @NotNull(message = "Поле цены билета не должно быть пустым!")
    @Positive(message = "Поле цены билета должно содержать только положительные цифры")
    @Schema(description = "Цена билета")
    private Integer price;
}
