package com.github.stanislavbukaevsky.purchasetransporttickets.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Класс-DTO для запроса от пользователя на информацию о транспортном маршруте
 */
@Data
@Schema(description = "Объект транспортного маршрута для запроса от пользователя")
public class RouteRequestDto {
    @NotEmpty(message = "Поле пункта отправления не должно быть пустым!")
    @Size(min = 2, max = 64, message = "Пункт отправления должен содержать от 2 до 64 символов!")
    @Schema(description = "Пункт отправления покупателя")
    private String departurePoint;
    @NotEmpty(message = "Поле пункта назначения не должно быть пустым!")
    @Size(min = 2, max = 64, message = "Пункт назначения должен содержать от 2 до 64 символов!")
    @Schema(description = "Пункт назначения покупателя")
    private String destination;
    @NotNull(message = "Поле продолжительности поездки не должно быть пустым!")
    @Positive(message = "Поле продолжительности поездки должно содержать только положительные цифры")
    @Schema(description = "Продолжительность поездки покупателя")
    private Integer durationInMinutes;
}
