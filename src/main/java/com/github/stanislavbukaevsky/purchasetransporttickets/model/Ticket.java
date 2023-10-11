package com.github.stanislavbukaevsky.purchasetransporttickets.model;

import com.github.stanislavbukaevsky.purchasetransporttickets.enums.TicketStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Модель представления билета
 */
@Data
public class Ticket implements Serializable {
    private Long id;
    private Route route;
    private Long routeId;
    private LocalDateTime dateTimeDeparture;
    private Integer seatNumber;
    private Integer price;
    private LocalDateTime dateTimeTicketIssuance;
    private User user;
    private Long userId;
    private TicketStatus ticketStatus;
}
