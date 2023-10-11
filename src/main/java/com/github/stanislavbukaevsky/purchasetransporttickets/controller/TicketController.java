package com.github.stanislavbukaevsky.purchasetransporttickets.controller;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.*;
import com.github.stanislavbukaevsky.purchasetransporttickets.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.*;

/**
 * Класс-контроллер для работы с транспортными билетами на платформе
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@Tag(name = "Работа с транспортными билетами", description = "Позволяет управлять методами по работе с транспортными билетами на платформе")
public class TicketController {
    private final TicketService ticketService;

    /**
     * Этот метод позволяет добавить новый транспортный билет на платформу.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param ticketRequestDto класс-DTO для запроса от пользователя на новый транспортный билет
     * @return Возвращает новый добавленный транспортный билет со всей информацией о нем
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый транспортный билет добавлен (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TicketResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод добавления нового транспортного билета на платформу",
            description = "Позволяет добавить новый транспортный билет на платформу")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public Mono<ResponseEntity<TicketResponseDto>> addTicket(@Valid @RequestBody TicketRequestDto ticketRequestDto) {
        TicketResponseDto ticketResponseDto = ticketService.addTicket(ticketRequestDto);
        log.info(ADD_TICKET_MESSAGE_LOGGER_CONTROLLER, ticketRequestDto);
        return Mono.just(ResponseEntity.ok(ticketResponseDto));
    }

    /**
     * Этот метод позволяет изменить информацию о транспортном билете на платформе.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param ticketRequestDto класс-DTO для запроса от пользователя на изменение транспортного билета
     * @param id               идентификатор транспортного билета
     * @return Возвращает измененный транспортный билет со всей информацией о нем
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Транспортный билет успешно изменен (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TicketResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод изменения информации о транспортном билете на платформе",
            description = "Позволяет изменить информацию о транспортном билете на платформе")
    @PatchMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public Mono<ResponseEntity<TicketResponseDto>> updateTicket(@Valid @RequestBody TicketRequestDto ticketRequestDto,
                                                                @Parameter(description = "Идентификатор транспортного билета")
                                                                @PathVariable @Positive Long id) {
        TicketResponseDto ticketResponseDto = ticketService.updateTicket(ticketRequestDto, id);
        log.info(UPDATE_TICKET_MESSAGE_LOGGER_CONTROLLER, ticketRequestDto, id);
        return Mono.just(ResponseEntity.ok(ticketResponseDto));
    }

    /**
     * Этот метод позволяет удалить транспортный билет с платформы.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param id идентификатор транспортного билета
     * @return Возвращает пустой Mono, если метод отработал корректно
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Транспортный билет успешно удален (OK)"),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для удаления транспортного билета с платформы",
            description = "Позволяет удалить транспортный билет с платформы")
    @DeleteMapping("/delete/{id}")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public Mono<ResponseEntity<Void>> deleteTicket(@Parameter(description = "Идентификатор транспортного билета")
                                                   @PathVariable @Positive Long id) {
        ticketService.deleteTicket(id);
        log.info(DELETE_TICKET_MESSAGE_LOGGER_CONTROLLER, id);
        return Mono.empty();
    }

    /**
     * Этот метод позволяет просмотреть отсортированный список транспортных билетов по дате и времени на платформе.
     * Этот метод поддерживает пагинацию с фильрацией количества записей
     *
     * @param dateAndTime класс-DTO для запроса от пользователя
     * @param page        номер страницы
     * @param size        количество записей
     * @return Возвращает список найденных транспортных билетов со всей информацией о них
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список транспортных билетов успешно найден (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TicketResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для просмотра отсортированного списка транспортных билетов по дате и времени на платформе",
            description = "Позволяет просмотреть отсортированный список транспортных билетов по дате и времени на платформе")
    @GetMapping(value = "/all-date-time", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Flux<List<TicketResponseDto>>> getTicketsByDateAndTime(@Valid @RequestBody TicketRequestByDateAndTimeDto dateAndTime,
                                                                                 @Parameter(description = "Номер страницы")
                                                                                 @RequestParam(required = false, defaultValue = "1")
                                                                                 @Positive int page,
                                                                                 @Parameter(description = "Количество записей на странице")
                                                                                 @RequestParam(required = false, defaultValue = "1")
                                                                                 @Positive int size) {
        List<TicketResponseDto> tickets = ticketService.getTicketsByDateAndTime(dateAndTime, page, size);
        log.info(GET_TICKETS_BY_DATE_AND_TIME_MESSAGE_LOGGER_CONTROLLER, dateAndTime, page, size);
        return ResponseEntity.ok(Flux.just(tickets));
    }

    /**
     * Этот метод позволяет просмотреть отсортированный список транспортных билетов по пункту отправления на платформе.
     * Этот метод поддерживает пагинацию с фильрацией количества записей
     *
     * @param departurePoint класс-DTO для запроса от пользователя
     * @param page           номер страницы
     * @param size           количество записей
     * @return Возвращает список найденных транспортных билетов со всей информацией о них
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список транспортных билетов успешно найден (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TicketResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для просмотра отсортированного списка транспортных билетов по пункту отправления на платформе",
            description = "Позволяет просмотреть отсортированный список транспортных билетов по пункту отправления на платформе")
    @GetMapping(value = "/all-departure-point", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Flux<List<TicketResponseDto>>> getTicketsByDeparturePoint(@Valid @RequestBody TicketRequestByDeparturePointDto departurePoint,
                                                                                    @Parameter(description = "Номер страницы")
                                                                                    @RequestParam(required = false, defaultValue = "1")
                                                                                    @Positive int page,
                                                                                    @Parameter(description = "Количество записей на странице")
                                                                                    @RequestParam(required = false, defaultValue = "1")
                                                                                    @Positive int size) {
        List<TicketResponseDto> tickets = ticketService.getTicketsByDeparturePoint(departurePoint, page, size);
        log.info(GET_TICKETS_BY_DEPARTURE_POINT_MESSAGE_LOGGER_CONTROLLER, departurePoint, page, size);
        return ResponseEntity.ok(Flux.just(tickets));
    }

    /**
     * Этот метод позволяет просмотреть отсортированный список транспортных билетов по пункту назначения на платформе.
     * Этот метод поддерживает пагинацию с фильрацией количества записей
     *
     * @param destination класс-DTO для запроса от пользователя
     * @param page        номер страницы
     * @param size        количество записей
     * @return Возвращает список найденных транспортных билетов со всей информацией о них
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список транспортных билетов успешно найден (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TicketResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для просмотра отсортированного списка транспортных билетов по пункту назначения на платформе",
            description = "Позволяет просмотреть отсортированный список транспортных билетов по пункту назначения на платформе")
    @GetMapping(value = "/all-destination", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Flux<List<TicketResponseDto>>> getTicketsByDestination(@Valid @RequestBody TicketRequestByDestinationDto destination,
                                                                                 @Parameter(description = "Номер страницы")
                                                                                 @RequestParam(required = false, defaultValue = "1")
                                                                                 @Positive int page,
                                                                                 @Parameter(description = "Количество записей на странице")
                                                                                 @RequestParam(required = false, defaultValue = "1")
                                                                                 @Positive int size) {
        List<TicketResponseDto> tickets = ticketService.getTicketsByDestination(destination, page, size);
        log.info(GET_TICKETS_BY_DESTINATION_MESSAGE_LOGGER_CONTROLLER, destination, page, size);
        return ResponseEntity.ok(Flux.just(tickets));
    }

    /**
     * Этот метод позволяет просмотреть отсортированный список транспортных билетов по названию компании перевозчика на платформе.
     * Этот метод поддерживает пагинацию с фильрацией количества записей
     *
     * @param companyName класс-DTO для запроса от пользователя
     * @param page        номер страницы
     * @param size        количество записей
     * @return Возвращает список найденных транспортных билетов со всей информацией о них
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список транспортных билетов успешно найден (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TicketResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для просмотра отсортированного списка транспортных билетов по названию компании перевозчика на платформе",
            description = "Позволяет просмотреть отсортированный список транспортных билетов по названию компании перевозчика на платформе")
    @GetMapping(value = "/all-company-carrier", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Flux<List<TicketResponseDto>>> getTicketsByCompanyNameCarrier(@Valid @RequestBody TicketRequestByCompanyNameCarrierDto companyName,
                                                                                        @Parameter(description = "Номер страницы")
                                                                                        @RequestParam(required = false, defaultValue = "1")
                                                                                        @Positive int page,
                                                                                        @Parameter(description = "Количество записей на странице")
                                                                                        @RequestParam(required = false, defaultValue = "1")
                                                                                        @Positive int size) {
        List<TicketResponseDto> tickets = ticketService.getTicketsByCompanyNameCarrier(companyName, page, size);
        log.info(GET_TICKETS_BY_COMPANY_NAME_CARRIER_MESSAGE_LOGGER_CONTROLLER, companyName, page, size);
        return ResponseEntity.ok(Flux.just(tickets));
    }

    /**
     * Этот метод позволяет купить транспортный билет зарегистрированному пользователю на платформе
     *
     * @param id             уникальный идентификатор билета
     * @param authentication объект аутентификации
     * @return Возвращает купленный транспортный билет со всей информацией о нем
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Транспортный билет успешно куплен (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TicketResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для покупки транспортного билета зарегистрированному пользователю на платформе",
            description = "Позволяет купить транспортный билет зарегистрированному пользователю на платформе")
    @PatchMapping(value = "/buying/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "JWT")
    public Mono<ResponseEntity<BuyingTicketResponseDto>> buyingTicket(@Parameter(description = "Уникальный идентификатор билета")
                                                                      @PathVariable
                                                                      @Positive Long id, Authentication authentication) {
        BuyingTicketResponseDto buyingTicketResponseDto = ticketService.buyingTicket(id, authentication);
        log.info(BUYING_TICKET_MESSAGE_LOGGER_CONTROLLER, id);
        return Mono.just(ResponseEntity.ok(buyingTicketResponseDto));
    }

    /**
     * Этот метод позволяет просмотреть отсортированный список купленных транспортных билетов пользователем на платформе.
     * Этот метод поддерживает пагинацию с фильрацией количества записей
     *
     * @param authentication объект аутентификации
     * @param page           номер страницы
     * @param size           количество записей
     * @return Возвращает список найденных транспортных билетов со всей информацией о них
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список транспортных билетов успешно найден (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TicketResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для просмотра отсортированного списка купленных транспортных билетов пользователем на платформе",
            description = "Позволяет просмотреть отсортированный список купленных транспортных билетов пользователем на платформе")
    @GetMapping(value = "/buying-user-id", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Flux<List<TicketResponseDto>>> findBuyingTicketsByUserId(Authentication authentication,
                                                                                   @Parameter(description = "Номер страницы")
                                                                                   @RequestParam(required = false, defaultValue = "1")
                                                                                   @Positive int page,
                                                                                   @Parameter(description = "Количество записей на странице")
                                                                                   @RequestParam(required = false, defaultValue = "1")
                                                                                   @Positive int size) {
        List<TicketResponseDto> tickets = ticketService.findBuyingTicketsByUserId(authentication, page, size);
        log.info(FIND_BUYING_TICKETS_BY_USER_ID_MESSAGE_LOGGER_CONTROLLER, page, size);
        return ResponseEntity.ok(Flux.just(tickets));
    }
}
