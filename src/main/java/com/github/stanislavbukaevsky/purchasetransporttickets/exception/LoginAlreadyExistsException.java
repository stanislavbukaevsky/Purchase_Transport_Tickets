package com.github.stanislavbukaevsky.purchasetransporttickets.exception;

/**
 * Класс-исключение, если произошла ошибка с логином пользователя. <br>
 * Наследуется от класса {@link RuntimeException}
 */
public class LoginAlreadyExistsException extends RuntimeException {
    public LoginAlreadyExistsException(String message) {
        super(message);
    }
}
