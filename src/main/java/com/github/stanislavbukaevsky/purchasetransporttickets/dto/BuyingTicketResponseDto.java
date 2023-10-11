package com.github.stanislavbukaevsky.purchasetransporttickets.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс-DTO для ответа пользователю с информацией о купленном транспортном билете
 */
@Data
@Schema(description = "Объект купленного транспортного билета для ответа пользователю")
public class BuyingTicketResponseDto {
    @Schema(description = "Уникальный идентификатор транспортного билета")
    private Long id;
    @Schema(description = "Уникальный идентификатор транспортного маршрута")
    private Long routeId;
    @Schema(description = "Пункт отправления покупателя")
    private String departurePoint;
    @Schema(description = "Пункт назначения покупателя")
    private String destination;
    @Schema(description = "Продолжительность поездки покупателя")
    private Integer durationInMinutes;
    @Schema(description = "Дата и время отправления")
    private LocalDateTime dateTimeDeparture;
    @Schema(description = "Дата и время покупки транспортного билета")
    private LocalDateTime dateTimeTicketIssuance;
    @Schema(description = "Посадочное места")
    private Integer seatNumber;
    @Schema(description = "Цена билета")
    private Integer price;
    @Schema(description = "Уникальный идентификатор транспортного перевозчика")
    private Long carrierId;
    @Schema(description = "Название компании перевозчика")
    private String companyName;
    @Schema(description = "Номер телефона компании перевозчика")
    private String phoneNumber;
    @Schema(description = "Статус билета")
    private String ticketStatus;
    @Schema(description = "Логин пользователя")
    private String login;
    @Schema(description = "Имя пользователя")
    private String firstName;
    @Schema(description = "Отчество пользователя")
    private String middleName;
    @Schema(description = "Фамилия пользователя")
    private String lastName;
    @Schema(description = "Роль пользователя")
    private String role;
}
