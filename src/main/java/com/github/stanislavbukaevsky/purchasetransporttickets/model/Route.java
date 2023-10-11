package com.github.stanislavbukaevsky.purchasetransporttickets.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Модель представления маршрута
 */
@Data
public class Route implements Serializable {
    private Long id;
    private String departurePoint;
    private String destination;
    private Carrier carrier;
    private Long carrierId;
    private Integer durationInMinutes;
}
