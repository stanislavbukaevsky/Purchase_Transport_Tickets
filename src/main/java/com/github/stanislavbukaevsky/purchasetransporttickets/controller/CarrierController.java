package com.github.stanislavbukaevsky.purchasetransporttickets.controller;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.CarrierRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.CarrierResponseDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.service.CarrierService;
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
 * Класс-контроллер для работы с транспортными перевозчиками на платформе
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/carriers")
@RequiredArgsConstructor
@Tag(name = "Работа с транспортными перевозчиками", description = "Позволяет управлять методами по работе с транспортными перевозчиками на платформе")
public class CarrierController {
    private final CarrierService carrierService;

    /**
     * Этот метод позволяет добавить нового транспортного перевозчика на платформу.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param carrierRequestDto класс-DTO для запроса от пользователя на нового транспортного перевозчика
     * @return Возвращает нового добавленного транспортного перевозчика со всей информацией о нем
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый транспортный перевозчик добавлен (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CarrierResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод добавления нового транспортного перевозчика на платформу",
            description = "Позволяет добавить нового транспортного перевозчика на платформу")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public Mono<ResponseEntity<CarrierResponseDto>> addCarrier(@Valid @RequestBody CarrierRequestDto carrierRequestDto) {
        CarrierResponseDto carrierResponseDto = carrierService.addCarrier(carrierRequestDto);
        log.info(ADD_CARRIER_MESSAGE_LOGGER_CONTROLLER, carrierRequestDto);
        return Mono.just(ResponseEntity.ok(carrierResponseDto));
    }

    /**
     * Этот метод позволяет изменить информацию о транспортном перевозчике на платформе.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param carrierRequestDto класс-DTO для запроса от пользователя на изменение транспортного перевозчика
     * @param id                идентификатор транспортного перевозчика
     * @return Возвращает измененного транспортного перевозчика со всей информацией о нем
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Транспортный перевозчик успешно изменен (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CarrierResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод изменения информации о транспортном перевозчике на платформе",
            description = "Позволяет изменить информацию о транспортном перевозчике на платформе")
    @PatchMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public Mono<ResponseEntity<CarrierResponseDto>> updateCarrier(@Valid @RequestBody CarrierRequestDto carrierRequestDto,
                                                                  @Parameter(description = "Идентификатор транспортного перевозчика")
                                                                  @PathVariable @Positive Long id) {
        CarrierResponseDto carrierResponseDto = carrierService.updateCarrier(carrierRequestDto, id);
        log.info(UPDATE_CARRIER_MESSAGE_LOGGER_CONTROLLER, carrierRequestDto, id);
        return Mono.just(ResponseEntity.ok(carrierResponseDto));
    }

    /**
     * Этот метод позволяет удалить транспортного перевозчика с платформы.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param id идентификатор транспортного перевозчика
     * @return Возвращает пустой Mono, если метод отработал корректно
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Транспортный перевозчик успешно удален (OK)"),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для удаления транспортного перевозчика с платформы",
            description = "Позволяет удалить транспортного перевозчика с платформы")
    @DeleteMapping("/delete/{id}")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public Mono<ResponseEntity<Void>> deleteCarrier(@Parameter(description = "Идентификатор транспортного перевозчика")
                                                    @PathVariable @Positive Long id) {
        carrierService.deleteCarrier(id);
        log.info(DELETE_CARRIER_MESSAGE_LOGGER_CONTROLLER, id);
        return Mono.empty();
    }
}
