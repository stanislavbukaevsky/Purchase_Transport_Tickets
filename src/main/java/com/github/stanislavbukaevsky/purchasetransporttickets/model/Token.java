package com.github.stanislavbukaevsky.purchasetransporttickets.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Модель представления токенов
 */
@Data
public class Token implements Serializable {
    private Long id;
    private String accessToken;
    private String refreshToken;
    private User user;
    private Long userId;
    private LocalDateTime expiresAtAccess;
    private LocalDateTime expiresAtRefresh;
}
