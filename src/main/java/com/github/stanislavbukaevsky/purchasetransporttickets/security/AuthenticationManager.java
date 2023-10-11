package com.github.stanislavbukaevsky.purchasetransporttickets.security;

import com.github.stanislavbukaevsky.purchasetransporttickets.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.AUTHENTICATION_MANAGER_MESSAGE_LOGGER_SERVICE;

/**
 * Этот класс служит для проверки аутентифицированного пользователя.
 * Реализует интерфейс {@link ReactiveAuthenticationManager}
 */
@Lazy
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final UserService userService;

    /**
     * Этот метод проверяет на существование пользователя в базе данных и является ли верными его учётные данные
     *
     * @param authentication объект аутентификации
     * @return Возвращает объект аутентификации обернутый в Mono
     */
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(AUTHENTICATION_MANAGER_MESSAGE_LOGGER_SERVICE, authentication);
        return userService.findUserByLogin(principal.getLogin()).map(user -> authentication);
    }
}
