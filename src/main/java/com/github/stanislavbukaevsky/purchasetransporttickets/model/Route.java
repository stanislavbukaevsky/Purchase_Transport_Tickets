package com.github.stanislavbukaevsky.purchasetransporttickets.model;

import lombok.Data;

/**
 * Модель представления маршрута
 */
@Data
public class Route {
    private Long id;
    private String departurePoint;
    private String destination;
    private Carrier carrier;
    private Integer durationInMinutes;
}
