package com.github.stanislavbukaevsky.purchasetransporttickets.service;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.CarrierRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.CarrierResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

/**
 * Сервис-интерфейс с методами для перевозчика в приложении.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface CarrierService {
    /**
     * Сигнатура метода для добавления новых перевозчиков в приложении.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param carrierRequestDto объект DTO с запросом от пользователя
     * @return Возвращает DTO с информацией о добавленном перевозчике
     */
    CarrierResponseDto addCarrier(@Valid CarrierRequestDto carrierRequestDto);

    /**
     * Сигнатура метода для изменения информации о перевозчике в приложении.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param carrierRequestDto объект DTO с запросом от пользователя
     * @param id                уникальный идентификатор перевозчика
     * @return Возвращает DTO с информацией об измененном перевозчике
     */
    CarrierResponseDto updateCarrier(@Valid CarrierRequestDto carrierRequestDto, @Positive Long id);

    /**
     * Сигнатура метода для удаления перевозчика из базы данных.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param id уникальный идентификатор перевозчика
     */
    void deleteCarrier(@Positive Long id);
}
