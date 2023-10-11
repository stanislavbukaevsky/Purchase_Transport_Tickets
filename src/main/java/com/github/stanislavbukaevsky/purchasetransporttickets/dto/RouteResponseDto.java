package com.github.stanislavbukaevsky.purchasetransporttickets.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Класс-DTO для ответа пользователю с информацией о транспортном маршруте
 */
@Data
@Schema(description = "Объект транспортного маршрута для ответа пользователю")
public class RouteResponseDto {
    @Schema(description = "Уникальный идентификатор маршрута")
    private Long id;
    @Schema(description = "Пункт отправления покупателя")
    private String departurePoint;
    @Schema(description = "Пункт назначения покупателя")
    private String destination;
    @Schema(description = "Продолжительность поездки покупателя")
    private Integer durationInMinutes;
    @Schema(description = "Уникальный идентификатор транспортного перевозчика")
    private Long carrierId;
    @Schema(description = "Название компании перевозчика")
    private String companyName;
    @Schema(description = "Номер телефона компании перевозчика")
    private String phoneNumber;
}
