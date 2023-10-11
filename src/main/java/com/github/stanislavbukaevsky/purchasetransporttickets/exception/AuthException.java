package com.github.stanislavbukaevsky.purchasetransporttickets.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Класс-исключение для аутентификации пользователей. <br>
 * Наследуется от класса {@link AuthenticationException}
 */
public class AuthException extends AuthenticationException {
    public AuthException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthException(String msg) {
        super(msg);
    }
}
