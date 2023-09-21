package com.github.stanislavbukaevsky.purchasetransporttickets.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Модель представления билета
 */
@Data
public class Ticket {
    private Long id;
    private Route route;
    private LocalDateTime dateTime;
    private Integer seatNumber;
    private Integer price;
}
