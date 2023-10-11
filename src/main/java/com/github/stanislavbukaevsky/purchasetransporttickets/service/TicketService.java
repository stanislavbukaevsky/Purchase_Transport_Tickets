package com.github.stanislavbukaevsky.purchasetransporttickets.service;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * Сервис-интерфейс с методами для билета в приложении.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface TicketService {
    /**
     * Сигнатура метода для добавления новых билетов в приложении.
     * Метод также сохраняет информацию в хранилище Redis.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param ticketRequestDto объект DTO с запросом от пользователя
     * @return Возвращает DTO с информацией о добавленном билете
     */
    TicketResponseDto addTicket(@Valid TicketRequestDto ticketRequestDto);

    /**
     * Сигнатура метода для изменения информации о билете в приложении.
     * Метод также изменяет информацию в хранилище Redis.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param ticketRequestDto объект DTO с запросом от пользователя
     * @param id               уникальный идентификатор билета
     * @return Возвращает DTO с информацией об измененном билете
     */
    TicketResponseDto updateTicket(@Valid TicketRequestDto ticketRequestDto, @Positive Long id);

    /**
     * Сигнатура метода для удаления билета из базы данных.
     * Метод также удаляет информацию из хранилища Redis.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param id уникальный идентификатор билета
     */
    void deleteTicket(@Positive Long id);

    /**
     * Сигнатура метода для просмотра отсортированного списока транспортных билетов по дате и времени на платформе.
     * Этот метод поддерживает пагинацию с фильрацией количества записей
     *
     * @param dateAndTime класс-DTO для запроса от пользователя
     * @param page        номер страницы
     * @param size        количество записей
     * @return Возвращает список найденных транспортных билетов со всей информацией о них
     */
    List<TicketResponseDto> getTicketsByDateAndTime(@Valid TicketRequestByDateAndTimeDto dateAndTime,
                                                    @Positive int page, @Positive int size);

    /**
     * Сигнатура метода для просмотра отсортированного списока транспортных билетов по пункту отправления на платформе.
     * Этот метод поддерживает пагинацию с фильрацией количества записей
     *
     * @param departurePoint класс-DTO для запроса от пользователя
     * @param page           номер страницы
     * @param size           количество записей
     * @return Возвращает список найденных транспортных билетов со всей информацией о них
     */
    List<TicketResponseDto> getTicketsByDeparturePoint(@Valid TicketRequestByDeparturePointDto departurePoint,
                                                       @Positive int page, @Positive int size);

    /**
     * Сигнатура метода для просмотра отсортированного списока транспортных билетов по пункту назначения на платформе.
     * Этот метод поддерживает пагинацию с фильрацией количества записей
     *
     * @param destination класс-DTO для запроса от пользователя
     * @param page        номер страницы
     * @param size        количество записей
     * @return Возвращает список найденных транспортных билетов со всей информацией о них
     */
    List<TicketResponseDto> getTicketsByDestination(@Valid TicketRequestByDestinationDto destination,
                                                    @Positive int page, @Positive int size);

    /**
     * Сигнатура метода для просмотра отсортированного списока транспортных билетов по названию компании перевозчика на платформе.
     * Этот метод поддерживает пагинацию с фильрацией количества записей
     *
     * @param companyName класс-DTO для запроса от пользователя
     * @param page        номер страницы
     * @param size        количество записей
     * @return Возвращает список найденных транспортных билетов со всей информацией о них
     */
    List<TicketResponseDto> getTicketsByCompanyNameCarrier(@Valid TicketRequestByCompanyNameCarrierDto companyName,
                                                           @Positive int page, @Positive int size);

    /**
     * Сигнатура метода для покупки транспортного билета зарегистрированному пользователю на платформе.
     * Этот метод отправляет данные о купленном билете в хранилище Redis.
     * Метод также отправляет данные о купленном билете в топик Kafka
     *
     * @param id             уникальный идентификатор билета
     * @param authentication объект аутентификации
     * @return Возвращает купленный транспортный билет со всей информацией о нем
     */
    BuyingTicketResponseDto buyingTicket(Long id, Authentication authentication);

    /**
     * Сигнатура метода для просмотра отсортированного списока купленных транспортных билетов пользователем на платформе.
     * Этот метод поддерживает пагинацию с фильрацией количества записей.
     * Метод изначально берет список купленных билетов из хранилища Redis
     *
     * @param authentication объект аутентификации
     * @param page           номер страницы
     * @param size           количество записей
     * @return Возвращает список найденных транспортных билетов со всей информацией о них
     */
    List<TicketResponseDto> findBuyingTicketsByUserId(Authentication authentication, @Positive int page, @Positive int size);
}
