package com.github.stanislavbukaevsky.purchasetransporttickets.service;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.AuthenticationRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.InfoAuthenticationUserDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RegistrationRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RegistrationResponseDto;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

/**
 * Сервис-интерфейс для регистрации и аутентификации пользователей в приложении.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface AuthService {
    /**
     * Сигнатура метода регистрации новых пользователей на платформе
     *
     * @param registrationRequestDto класс-DTO для регистрации пользователя на платформе
     * @return Возвращает DTO с информацией о зарегистрированном пользователе
     */
    Mono<RegistrationResponseDto> registration(@Valid RegistrationRequestDto registrationRequestDto);

    /**
     * Сигнатура метода аутентификации пользователей на платформе
     *
     * @param authenticationRequestDto класс-DTO для аутентификации пользователя на платформе
     * @return Возвращает DTO с информацией об аутентифицированном пользователе на платформе
     */
    Mono<InfoAuthenticationUserDto> authentication(@Valid AuthenticationRequestDto authenticationRequestDto);
}
