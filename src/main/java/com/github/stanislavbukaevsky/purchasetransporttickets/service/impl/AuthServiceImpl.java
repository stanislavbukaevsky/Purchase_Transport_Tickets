package com.github.stanislavbukaevsky.purchasetransporttickets.service.impl;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.AuthenticationRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.InfoAuthenticationUserDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RegistrationRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RegistrationResponseDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.enums.Role;
import com.github.stanislavbukaevsky.purchasetransporttickets.exception.AuthException;
import com.github.stanislavbukaevsky.purchasetransporttickets.exception.LoginAlreadyExistsException;
import com.github.stanislavbukaevsky.purchasetransporttickets.exception.MonoNoContentException;
import com.github.stanislavbukaevsky.purchasetransporttickets.mapper.TokenMapper;
import com.github.stanislavbukaevsky.purchasetransporttickets.mapper.UserMapper;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.Token;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.User;
import com.github.stanislavbukaevsky.purchasetransporttickets.repository.TokenRepository;
import com.github.stanislavbukaevsky.purchasetransporttickets.repository.UserRepository;
import com.github.stanislavbukaevsky.purchasetransporttickets.service.AuthService;
import com.github.stanislavbukaevsky.purchasetransporttickets.service.TokenService;
import com.github.stanislavbukaevsky.purchasetransporttickets.token.TokenDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.ExceptionTextMessageConstant.*;
import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.*;

/**
 * Сервис-класс с бизнес-логикой для регистрации и аутентификации пользователей в приложении.
 * Реализует интерфейс {@link AuthService}
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final UserMapper userMapper;
    private final TokenMapper tokenMapper;
    private final TokenService tokenService;
    private final TokenDetailsService tokenDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Реализация метода регистрации новых пользователей на платформе
     *
     * @param registrationRequestDto класс-DTO для регистрации пользователя на платформе
     * @return Возвращает DTO с информацией о зарегистрированном пользователе
     */
    @Override
    public Mono<RegistrationResponseDto> registration(@Valid RegistrationRequestDto registrationRequestDto) {
        Boolean existsUser = userRepository.existsUserByLogin(registrationRequestDto.getLogin());
        if (existsUser) {
            throw new LoginAlreadyExistsException(REGISTRATION_MESSAGE_EXCEPTION_SERVICE);
        }

        return Mono.just(userMapper.toUserModel(registrationRequestDto))
                .flatMap(user -> {
                    User result = userRepository.save(user);
                    log.info(REGISTRATION_MESSAGE_LOGGER_SERVICE, result.getLogin());
                    return Mono.just(userMapper.toRegistrationResponseDto(result));
                }).switchIfEmpty(Mono.error(new MonoNoContentException(MONO_NO_CONTENT_EXCEPTION_MESSAGE_SERVICE)));
    }

    /**
     * Реализация метода аутентификации пользователей на платформе
     *
     * @param authenticationRequestDto класс-DTO для аутентификации пользователя на платформе
     * @return Возвращает DTO с информацией об аутентифицированном пользователе на платформе
     */
    @Override
    public Mono<InfoAuthenticationUserDto> authentication(@Valid AuthenticationRequestDto authenticationRequestDto) {
        return Mono.just(userRepository.findUserByLogin(authenticationRequestDto.getLogin()))
                .flatMap(user -> {
                    if (passwordEncoder.matches(authenticationRequestDto.getPassword(), user.getPassword())) {
                        log.info(AUTHENTICATION_MESSAGE_LOGGER_SERVICE, authenticationRequestDto.getLogin());
                        return Mono.just(generatingInfoAuthenticationUserDto(user));
                    }
                    return Mono.error(new AuthException(AUTH_EXCEPTION_AUTHENTICATION_MESSAGE_SERVICE));
                });
    }

    /**
     * Приватный метод для генерации ответа с личной информацией об аутентифицированном пользователе
     *
     * @param user модель пользователя
     * @return Возвращает сгенерированный ответ с полной информацией о пользователе через DTO-класс
     */
    private InfoAuthenticationUserDto generatingInfoAuthenticationUserDto(User user) {
        final Long id = user.getId();
        final String login = user.getLogin();
        final String password = user.getPassword();
        final String firstName = user.getFirstName();
        final String middleName = user.getMiddleName();
        final String lastName = user.getLastName();
        final Role role = user.getRole();
        final String accessToken = tokenDetailsService.generateAccessToken(user);
        final String refreshToken = tokenDetailsService.generateRefreshToken(user);
        final LocalDateTime expiresAtAccess = LocalDateTime.ofInstant(
                        tokenDetailsService.getAccessExpiration().toInstant(), ZoneId.systemDefault())
                .truncatedTo(ChronoUnit.MINUTES);
        final LocalDateTime expiresAtRefresh = LocalDateTime.ofInstant(
                        tokenDetailsService.getRefreshExpiration().toInstant(), ZoneId.systemDefault())
                .truncatedTo(ChronoUnit.MINUTES);
        Boolean existsToken = tokenRepository.existsTokenByUserId(id);

        if (existsToken) {
            Token token = tokenRepository.findTokenByUserId(id);
            token.setAccessToken(accessToken);
            token.setExpiresAtAccess(expiresAtAccess);
            token.setRefreshToken(refreshToken);
            token.setExpiresAtRefresh(expiresAtRefresh);
            tokenRepository.update(token);
        } else {
            tokenService.addToken(id, accessToken, refreshToken, user, expiresAtAccess, expiresAtRefresh);
        }

        log.info(GENERATING_INFO_AUTHENTICATION_USER_DTO_MESSAGE_LOGGER_SERVICE, login);
        return tokenMapper.toInfoAuthenticationUserDto(id,
                login,
                password,
                firstName,
                middleName,
                lastName,
                role,
                accessToken,
                refreshToken,
                expiresAtAccess,
                expiresAtRefresh);
    }
}
