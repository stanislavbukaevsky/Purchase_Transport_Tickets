package com.github.stanislavbukaevsky.purchasetransporttickets.controller;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RouteRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RouteResponseDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RouteUpdateRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.service.RouteService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.*;

/**
 * Класс-контроллер для работы с транспортными маршрутами на платформе
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
@Tag(name = "Работа с транспортными маршрутами", description = "Позволяет управлять методами по работе с транспортными маршрутами на платформе")
public class RouteController {
    private final RouteService routeService;

    /**
     * Этот метод позволяет добавить новый транспортный маршрут на платформу.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param routeRequestDto класс-DTO для запроса от пользователя на новый транспортный маршрут
     * @param id              идентификатор транспортного перевозчика
     * @return Возвращает новый добавленный транспортный маршрут со всей информацией о нем
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый транспортный маршрут добавлен (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RouteResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод добавления нового транспортного маршрута на платформу",
            description = "Позволяет добавить новый транспортный маршрут на платформу")
    @PostMapping(value = "/add/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public Mono<ResponseEntity<RouteResponseDto>> addRoute(@Valid @RequestBody RouteRequestDto routeRequestDto,
                                                           @Parameter(description = "Идентификатор транспортного перевозчика")
                                                           @PathVariable @Positive Long id) {
        RouteResponseDto routeResponseDto = routeService.addRoute(routeRequestDto, id);
        log.info(ADD_ROUTE_MESSAGE_LOGGER_CONTROLLER, routeRequestDto, id);
        return Mono.just(ResponseEntity.ok(routeResponseDto));
    }

    /**
     * Этот метод позволяет изменить информацию о транспортном маршруте на платформе.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param routeUpdateRequestDto класс-DTO для запроса от пользователя на изменение транспортного маршрута
     * @param id                    идентификатор транспортного маршрута
     * @return Возвращает измененный транспортный маршрут со всей информацией о нем
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Транспортный маршрут успешно изменен (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RouteResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод изменения информации о транспортном маршруте на платформе",
            description = "Позволяет изменить информацию о транспортном маршруте на платформе")
    @PatchMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public Mono<ResponseEntity<RouteResponseDto>> updateRoute(@Valid @RequestBody RouteUpdateRequestDto routeUpdateRequestDto,
                                                              @Parameter(description = "Идентификатор транспортного маршрута")
                                                              @PathVariable @Positive Long id) {
        RouteResponseDto routeResponseDto = routeService.updateRoute(routeUpdateRequestDto, id);
        log.info(UPDATE_ROUTE_MESSAGE_LOGGER_CONTROLLER, routeUpdateRequestDto, id);
        return Mono.just(ResponseEntity.ok(routeResponseDto));
    }

    /**
     * Этот метод позволяет удалить транспортный маршрут с платформы.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param id идентификатор транспортного маршрута
     * @return Возвращает пустой Mono, если метод отработал корректно
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Транспортный маршрут успешно удален (OK)"),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для удаления транспортного маршрута с платформы",
            description = "Позволяет удалить транспортный маршрут с платформы")
    @DeleteMapping("/delete/{id}")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public Mono<ResponseEntity<Void>> deleteRoute(@Parameter(description = "Идентификатор транспортного маршрута")
                                                  @PathVariable @Positive Long id) {
        routeService.deleteRoute(id);
        log.info(DELETE_ROUTE_MESSAGE_LOGGER_CONTROLLER, id);
        return Mono.empty();
    }
}
