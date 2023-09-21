package com.github.stanislavbukaevsky.purchasetransporttickets.model;

import lombok.Data;

/**
 * Модель представления перевозчика
 */
@Data
public class Carrier {
    private Long id;
    private String companyName;
    private String phoneNumber;
}
