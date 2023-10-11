package com.github.stanislavbukaevsky.purchasetransporttickets.controller;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.AuthenticationRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.InfoAuthenticationUserDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RegistrationRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RegistrationResponseDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.AUTHENTICATION_MESSAGE_LOGGER_CONTROLLER;
import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.REGISTRATION_MESSAGE_LOGGER_CONTROLLER;

/**
 * Класс-контроллер для работы с регистрацией и аутентификацией пользователей на платформе
 */
@Slf4j
@RestController
@RequestMapping("/inputs")
@RequiredArgsConstructor
@Tag(name = "Работа с регистрацией и аутентификацией", description = "Позволяет управлять методами по работе с регистрацией и аутентификацией пользователей на платформе")
public class AuthController {
    private final AuthService authService;

    /**
     * Этот метод позволяет зарегистрировать нового пользователя на платформе
     *
     * @param registrationRequestDto класс-DTO для регистрации пользователя на платформе
     * @return Возвращает зарегистрированного пользователя и всю информацию о нем
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый пользователь зарегистрирован (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RegistrationResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод регистрации пользователей на платформе",
            description = "Позволяет зарегистрироваться новому пользователю на платформе")
    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<RegistrationResponseDto>> registration(@Valid @RequestBody RegistrationRequestDto registrationRequestDto) {
        Mono<RegistrationResponseDto> addUser = authService.registration(registrationRequestDto);
        log.info(REGISTRATION_MESSAGE_LOGGER_CONTROLLER, registrationRequestDto.getLogin());
        return ResponseEntity.ok(addUser);
    }

    /**
     * Этот метод позволяет аутентифицироваться (войти в приложение) пользователю на платформе
     *
     * @param authenticationRequestDto класс-DTO для аутентификации пользователя на платформе
     * @return Возвращает аутентифицированного пользователя, если такой существует
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно аутентифицирован (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = InfoAuthenticationUserDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод аутентификации пользователей на платформе",
            description = "Позволяет аутентифицироваться зарегистрированному пользователю на платформе")
    @PostMapping(value = "/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<InfoAuthenticationUserDto>> authentication(@Valid @RequestBody AuthenticationRequestDto authenticationRequestDto) {
        Mono<InfoAuthenticationUserDto> token = authService.authentication(authenticationRequestDto);
        log.info(AUTHENTICATION_MESSAGE_LOGGER_CONTROLLER, authenticationRequestDto.getLogin());
        return ResponseEntity.ok(token);
    }
}
