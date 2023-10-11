package com.github.stanislavbukaevsky.purchasetransporttickets.service;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RouteRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RouteResponseDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RouteUpdateRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

/**
 * Сервис-интерфейс с методами для маршрута в приложении.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface RouteService {
    /**
     * Сигнатура метода для добавления новых маршрутов в приложении.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param routeRequestDto объект DTO с запросом от пользователя
     * @param id              уникальный идентификатор перевозчика
     * @return Возвращает DTO с информацией о добавленном маршруте
     */
    RouteResponseDto addRoute(@Valid RouteRequestDto routeRequestDto, @Positive Long id);

    /**
     * Сигнатура метода для изменения информации о маршруте в приложении.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param routeUpdateRequestDto объект DTO с запросом от пользователя
     * @param id                    уникальный идентификатор маршрута
     * @return Возвращает DTO с информацией об измененном маршруте
     */
    RouteResponseDto updateRoute(@Valid RouteUpdateRequestDto routeUpdateRequestDto, @Positive Long id);

    /**
     * Сигнатура метода для удаления маршрута из базы данных.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param id уникальный идентификатор маршрута
     */
    void deleteRoute(@Positive Long id);
}
