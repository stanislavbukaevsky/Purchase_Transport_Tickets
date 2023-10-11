package com.github.stanislavbukaevsky.purchasetransporttickets.service.impl;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.*;
import com.github.stanislavbukaevsky.purchasetransporttickets.enums.TicketStatus;
import com.github.stanislavbukaevsky.purchasetransporttickets.exception.TicketNotFoundException;
import com.github.stanislavbukaevsky.purchasetransporttickets.kafka.KafkaSenderService;
import com.github.stanislavbukaevsky.purchasetransporttickets.mapper.TicketMapper;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.Carrier;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.Route;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.Ticket;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.User;
import com.github.stanislavbukaevsky.purchasetransporttickets.repository.*;
import com.github.stanislavbukaevsky.purchasetransporttickets.security.CustomPrincipal;
import com.github.stanislavbukaevsky.purchasetransporttickets.service.TicketService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.ExceptionTextMessageConstant.*;
import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.*;

/**
 * Сервис-класс с бизнес-логикой для билета в приложении.
 * Реализует интерфейс {@link TicketService}
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final CarrierRepository carrierRepository;
    private final RouteRepository routeRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final RedisRepository redisRepository;
    private final KafkaSenderService kafkaSenderService;
    private final TicketMapper ticketMapper;

    /**
     * Реализация метода для добавления новых билетов в приложении.
     * Метод также сохраняет информацию в хранилище Redis.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param ticketRequestDto объект DTO с запросом от пользователя
     * @return Возвращает DTO с информацией о добавленном билете
     */
    @Override
    public TicketResponseDto addTicket(@Valid TicketRequestDto ticketRequestDto) {
        Route route = routeRepository.findCustomRouteById(ticketRequestDto.getRouteId());
        Carrier carrier = carrierRepository.findCarrierById(route.getCarrierId());
        Ticket ticket = ticketMapper.toTicketModel(ticketRequestDto);
        ticket.setRoute(route);
        ticket.setDateTimeDeparture(parseDateAndTime(ticketRequestDto.getDateTimeDeparture()));
        ticket.setDateTimeTicketIssuance(LocalDateTime.now());

        Ticket result = ticketRepository.save(ticket);
        redisRepository.save(result);
        log.info(ADD_TICKET_MESSAGE_LOGGER_SERVICE, ticketRequestDto);
        return formingTicketResponseDto(route, carrier, result);
    }

    /**
     * Реализация метода для изменения информации о билете в приложении.
     * Метод также изменяет информацию в хранилище Redis.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param ticketRequestDto объект DTO с запросом от пользователя
     * @param id               уникальный идентификатор билета
     * @return Возвращает DTO с информацией об измененном билете
     */
    @Override
    public TicketResponseDto updateTicket(@Valid TicketRequestDto ticketRequestDto, @Positive Long id) {
        Ticket ticket = ticketRepository.findTicketById(id);
        Route route = routeRepository.findCustomRouteById(ticketRequestDto.getRouteId());
        Carrier carrier = carrierRepository.findCarrierById(route.getCarrierId());
        ticket.setRoute(route);
        ticket.setDateTimeDeparture(parseDateAndTime(ticketRequestDto.getDateTimeDeparture()));
        ticket.setDateTimeTicketIssuance(LocalDateTime.now());

        Ticket result = ticketRepository.update(ticket);
        redisRepository.save(result);
        log.info(UPDATE_TICKET_MESSAGE_LOGGER_SERVICE, ticketRequestDto, id);
        return formingTicketResponseDto(route, carrier, result);
    }

    /**
     * Реализация метода для удаления билета из базы данных.
     * Метод также удаляет информацию из хранилища Redis.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param id уникальный идентификатор билета
     */
    @Override
    public void deleteTicket(@Positive Long id) {
        Ticket ticket = ticketRepository.findTicketById(id);
        redisRepository.delete(ticket.getId());
        ticketRepository.deleteById(ticket.getId());
        log.info(DELETE_TICKET_MESSAGE_LOGGER_SERVICE, id);
    }

    /**
     * Реализация метода для просмотра отсортированного списока транспортных билетов по дате и времени на платформе.
     * Этот метод поддерживает пагинацию с фильрацией количества записей
     *
     * @param dateAndTime класс-DTO для запроса от пользователя
     * @param page        номер страницы
     * @param size        количество записей
     * @return Возвращает список найденных транспортных билетов со всей информацией о них
     */
    @Override
    public List<TicketResponseDto> getTicketsByDateAndTime(@Valid TicketRequestByDateAndTimeDto dateAndTime,
                                                           @Positive int page, @Positive int size) {
        LocalDateTime dateTimeDeparture = parseDateAndTime(dateAndTime.getDateTimeRequest());
        Pageable pageable = PageRequest.of(page - 1, size);
        List<Ticket> tickets = ticketRepository.findTicketsByDateAndTimeDeparture(dateTimeDeparture, pageable);
        List<TicketResponseDto> result = new ArrayList<>();

        if (!tickets.isEmpty()) {
            for (Ticket ticket : tickets) {
                if (ticket.getTicketStatus().equals(TicketStatus.AVAILABLE_FOR_SALE)) {
                    Route route = routeRepository.findCustomRouteById(ticket.getRouteId());
                    Carrier carrier = carrierRepository.findCarrierById(route.getCarrierId());
                    TicketResponseDto ticketResponseDto = formingTicketResponseDto(route, carrier, ticket);
                    result.add(ticketResponseDto);
                }
            }
            log.info(GET_TICKETS_BY_DATE_AND_TIME_MESSAGE_LOGGER_SERVICE, dateAndTime, page, size);
            return result;
        }
        throw new NullPointerException(NULL_POINTER_EXCEPTION_MESSAGE_SERVICE);
    }

    /**
     * Реализация метода для просмотра отсортированного списока транспортных билетов по пункту отправления на платформе.
     * Этот метод поддерживает пагинацию с фильрацией количества записей
     *
     * @param departurePoint класс-DTO для запроса от пользователя
     * @param page           номер страницы
     * @param size           количество записей
     * @return Возвращает список найденных транспортных билетов со всей информацией о них
     */
    @Override
    public List<TicketResponseDto> getTicketsByDeparturePoint(@Valid TicketRequestByDeparturePointDto departurePoint,
                                                              @Positive int page, @Positive int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        List<Route> routes = routeRepository.findRoutesByDeparturePoint(departurePoint.getDeparturePoint(), pageable);
        log.info(GET_TICKETS_BY_DEPARTURE_POINT_MESSAGE_LOGGER_SERVICE, departurePoint, page, size);
        return formingTicketsResponseDto(routes, pageable);
    }

    /**
     * Реализация метода для просмотра отсортированного списока транспортных билетов по пункту назначения на платформе.
     * Этот метод поддерживает пагинацию с фильрацией количества записей
     *
     * @param destination класс-DTO для запроса от пользователя
     * @param page        номер страницы
     * @param size        количество записей
     * @return Возвращает список найденных транспортных билетов со всей информацией о них
     */
    @Override
    public List<TicketResponseDto> getTicketsByDestination(@Valid TicketRequestByDestinationDto destination,
                                                           @Positive int page, @Positive int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        List<Route> routes = routeRepository.findRoutesByDestination(destination.getDestination(), pageable);
        log.info(GET_TICKETS_BY_DESTINATION_MESSAGE_LOGGER_SERVICE, destination, page, size);
        return formingTicketsResponseDto(routes, pageable);
    }

    /**
     * Реализация метода для просмотра отсортированного списока транспортных билетов по названию компании перевозчика на платформе.
     * Этот метод поддерживает пагинацию с фильрацией количества записей
     *
     * @param companyName класс-DTO для запроса от пользователя
     * @param page        номер страницы
     * @param size        количество записей
     * @return Возвращает список найденных транспортных билетов со всей информацией о них
     */
    @Override
    public List<TicketResponseDto> getTicketsByCompanyNameCarrier(@Valid TicketRequestByCompanyNameCarrierDto companyName,
                                                                  @Positive int page, @Positive int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Carrier carrier = carrierRepository.findCarrierByCompanyName(companyName.getCompanyName());
        List<Route> routes = routeRepository.findRoutesByCompanyNameCarrier(carrier.getId(), pageable);
        List<TicketResponseDto> result = new ArrayList<>();
        if (!routes.isEmpty()) {
            for (Route route : routes) {
                List<Ticket> tickets = ticketRepository.findTicketsByRouteId(route.getId(), pageable);
                for (Ticket ticket : tickets) {
                    if (ticket.getTicketStatus().equals(TicketStatus.AVAILABLE_FOR_SALE)) {
                        TicketResponseDto ticketResponseDto = formingTicketResponseDto(route, carrier, ticket);
                        result.add(ticketResponseDto);
                    }
                }
            }
            log.info(GET_TICKETS_BY_COMPANY_NAME_CARRIER_MESSAGE_LOGGER_SERVICE, companyName, page, size);
            return result;
        }
        throw new NullPointerException(NULL_POINTER_EXCEPTION_MESSAGE_SERVICE);
    }

    /**
     * Реализация метода для покупки транспортного билета зарегистрированному пользователю на платформе.
     * Этот метод отправляет данные о купленном билете в хранилище Redis.
     * Метод также отправляет данные о купленном билете в топик Kafka
     *
     * @param id             уникальный идентификатор билета
     * @param authentication объект аутентификации
     * @return Возвращает купленный транспортный билет со всей информацией о нем
     */
    @Override
    @Caching(put = @CachePut(key = "#id", value = "buying-tickets"))
    public BuyingTicketResponseDto buyingTicket(Long id, Authentication authentication) {
        Ticket ticket = ticketRepository.findTicketById(id);
        Route route = routeRepository.findRouteById(ticket.getRouteId());
        Carrier carrier = carrierRepository.findCarrierById(route.getCarrierId());
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        User user = userRepository.findUserByLogin(principal.getLogin());

        if (ticket.getTicketStatus().equals(TicketStatus.AVAILABLE_FOR_SALE)) {
            ticket.setUser(user);
            ticket.setUserId(user.getId());
            Ticket result = ticketRepository.updateStatusTicket(ticket);
            redisRepository.save(result);
            BuyingTicketResponseDto buyingTicketResponseDto = formingBuyingTicketResponseDto(route, carrier, result, user);
            kafkaSenderService.send(buyingTicketResponseDto);
            log.info(BUYING_TICKET_MESSAGE_LOGGER_SERVICE, id);
            return buyingTicketResponseDto;
        }
        throw new TicketNotFoundException(TICKET_NOT_FOUND_EXCEPTION_MESSAGE_SERVICE);
    }

    /**
     * Реализация метода для просмотра отсортированного списока купленных транспортных билетов пользователем на платформе.
     * Этот метод поддерживает пагинацию с фильрацией количества записей.
     * Метод изначально берет список купленных билетов из хранилища Redis
     *
     * @param authentication объект аутентификации
     * @param page           номер страницы
     * @param size           количество записей
     * @return Возвращает список найденных транспортных билетов со всей информацией о них
     */
    @Override
    @Cacheable("buying-tickets")
    public List<TicketResponseDto> findBuyingTicketsByUserId(Authentication authentication, @Positive int page, @Positive int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        User user = userRepository.findUserByLogin(principal.getLogin());
        List<Ticket> tickets = ticketRepository.findTicketsByUserId(user.getId(), pageable);
        List<Ticket> redisTickets = redisRepository.findAllTickets().values().stream().toList();
        List<TicketResponseDto> result = new ArrayList<>();

        if (redisTickets.size() != tickets.size()) {
            for (Ticket ticket : tickets) {
                if (!redisTickets.contains(ticket)) {
                    redisRepository.save(ticket);
                }
                Route route = routeRepository.findRouteById(ticket.getRouteId());
                Carrier carrier = carrierRepository.findCarrierById(route.getCarrierId());
                TicketResponseDto ticketResponseDto = formingTicketResponseDto(route, carrier, ticket);
                result.add(ticketResponseDto);
            }
        } else {
            for (Ticket ticket : redisTickets) {
                if (ticket.getTicketStatus().equals(TicketStatus.NOT_ON_SALE)) {
                    Route route = routeRepository.findRouteById(ticket.getRouteId());
                    Carrier carrier = carrierRepository.findCarrierById(route.getCarrierId());
                    TicketResponseDto ticketResponseDto = formingTicketResponseDto(route, carrier, ticket);
                    result.add(ticketResponseDto);
                }
            }
        }

        log.info(FIND_BUYING_TICKETS_BY_USER_ID_MESSAGE_LOGGER_SERVICE, page, size);
        return result;
    }

    /**
     * Приватный метод для преобразования даты и времени в строковом виде к формату LocalDateTime
     *
     * @param dateTimeDeparture строка даты и времени
     * @return Возвращает LocalDateTime с установленной датой и временем
     */
    private LocalDateTime parseDateAndTime(String dateTimeDeparture) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        log.info(PARSE_DATE_AND_TIME_MESSAGE_LOGGER_SERVICE, dateTimeDeparture);
        return LocalDateTime.parse(dateTimeDeparture, formatter);
    }

    /**
     * Приватный метод для генерации ответа с информацией о транспортном билете
     *
     * @param route   модель маршрута
     * @param carrier модель перевозчика
     * @param ticket  модель билета
     * @return Возвращает сгенерированный ответ с полной информацией о транспортном билете через DTO-класс
     */
    private TicketResponseDto formingTicketResponseDto(Route route, Carrier carrier, Ticket ticket) {
        TicketResponseDto ticketResponseDto = ticketMapper.toTicketResponseDto(ticket);
        ticketResponseDto.setRouteId(route.getId());
        ticketResponseDto.setDeparturePoint(route.getDeparturePoint());
        ticketResponseDto.setDestination(route.getDestination());
        ticketResponseDto.setDurationInMinutes(route.getDurationInMinutes());
        ticketResponseDto.setCarrierId(carrier.getId());
        ticketResponseDto.setCompanyName(carrier.getCompanyName());
        ticketResponseDto.setPhoneNumber(carrier.getPhoneNumber());

        log.info(FORMING_TICKET_RESPONSE_DTO_MESSAGE_LOGGER_SERVICE);
        return ticketResponseDto;
    }

    /**
     * Приватный метод для генерации ответа с информацией о купленном транспортном билете
     *
     * @param route   модель маршрута
     * @param carrier модель перевозчика
     * @param ticket  модель билета
     * @param user    модель пользователя
     * @return Возвращает сгенерированный ответ с полной информацией о купленном транспортном билете через DTO-класс
     */
    private BuyingTicketResponseDto formingBuyingTicketResponseDto(Route route, Carrier carrier, Ticket ticket, User user) {
        BuyingTicketResponseDto buyingTicketResponseDto = ticketMapper.toBuyingTicketResponseDto(ticket);
        buyingTicketResponseDto.setRouteId(route.getId());
        buyingTicketResponseDto.setDeparturePoint(route.getDeparturePoint());
        buyingTicketResponseDto.setDestination(route.getDestination());
        buyingTicketResponseDto.setDurationInMinutes(route.getDurationInMinutes());
        buyingTicketResponseDto.setCarrierId(carrier.getId());
        buyingTicketResponseDto.setCompanyName(carrier.getCompanyName());
        buyingTicketResponseDto.setPhoneNumber(carrier.getPhoneNumber());
        buyingTicketResponseDto.setLogin(user.getLogin());
        buyingTicketResponseDto.setFirstName(user.getFirstName());
        buyingTicketResponseDto.setMiddleName(user.getMiddleName());
        buyingTicketResponseDto.setLastName(user.getLastName());
        buyingTicketResponseDto.setRole(user.getRole().getDescription());

        log.info(FORMING_BUYING_TICKET_RESPONSE_DTO_MESSAGE_LOGGER_SERVICE);
        return buyingTicketResponseDto;
    }

    /**
     * Приватный метод для генерации списка ответов с информацией о транспортном билете
     *
     * @param routes   список маршрутов
     * @param pageable объект пагинации
     * @return Возвращает список сгенерированных ответов с полной информацией о транспортных билетах через DTO-класс
     */
    private List<TicketResponseDto> formingTicketsResponseDto(List<Route> routes, Pageable pageable) {
        List<TicketResponseDto> result = new ArrayList<>();
        if (!routes.isEmpty()) {

            for (Route route : routes) {
                List<Ticket> tickets = ticketRepository.findTicketsByRouteId(route.getId(), pageable);
                for (Ticket ticket : tickets) {
                    if (ticket.getTicketStatus().equals(TicketStatus.AVAILABLE_FOR_SALE)) {
                        Carrier carrier = carrierRepository.findCarrierById(route.getCarrierId());
                        TicketResponseDto ticketResponseDto = formingTicketResponseDto(route, carrier, ticket);
                        result.add(ticketResponseDto);
                    }
                }
            }
            log.info(FORMING_TICKETS_RESPONSE_DTO_MESSAGE_LOGGER_SERVICE);
            return result;
        }
        throw new NullPointerException(NULL_POINTER_EXCEPTION_THREE_MESSAGE_SERVICE);
    }
}
