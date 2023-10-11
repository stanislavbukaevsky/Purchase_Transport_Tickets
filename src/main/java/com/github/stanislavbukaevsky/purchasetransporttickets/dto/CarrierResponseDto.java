package com.github.stanislavbukaevsky.purchasetransporttickets.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Класс-DTO для ответа пользователю с информацией о транспортном перевозчике
 */
@Data
@Schema(description = "Объект перевозчика для ответа пользователю")
public class CarrierResponseDto {
    @Schema(description = "Уникальный идентификатор транспортного перевозчика")
    private Long id;
    @Schema(description = "Название компании перевозчика")
    private String companyName;
    @Schema(description = "Номер телефона компании перевозчика")
    private String phoneNumber;
}
