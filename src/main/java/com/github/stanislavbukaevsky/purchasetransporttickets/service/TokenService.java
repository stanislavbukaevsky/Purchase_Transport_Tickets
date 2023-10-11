package com.github.stanislavbukaevsky.purchasetransporttickets.service;

import com.github.stanislavbukaevsky.purchasetransporttickets.model.User;

import java.time.LocalDateTime;

/**
 * Сервис-интерфейс с методами для JWT токенов в приложении.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface TokenService {
    /**
     * Сигнатура метода для добавления новых JWT токенов в приложении
     *
     * @param userId           уникальный идентификатор пользователя
     * @param accessToken      access токен
     * @param refreshToken     refresh токен
     * @param user             модель пользователя
     * @param expiresAtAccess  дата истечения срока действия access токена
     * @param expiresAtRefresh дата истечения срока действия refresh токена
     */
    void addToken(Long userId,
                  String accessToken,
                  String refreshToken,
                  User user,
                  LocalDateTime expiresAtAccess,
                  LocalDateTime expiresAtRefresh);
}
