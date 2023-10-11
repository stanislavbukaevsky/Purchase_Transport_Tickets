package com.github.stanislavbukaevsky.purchasetransporttickets.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Модель представления перевозчика
 */
@Data
public class Carrier implements Serializable {
    private Long id;
    private String companyName;
    private String phoneNumber;
}
