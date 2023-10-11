package com.github.stanislavbukaevsky.purchasetransporttickets.exception;

/**
 * Класс-исключение, если произошла ошибка с блоком Mono. <br>
 * Наследуется от класса {@link RuntimeException}
 */
public class MonoNoContentException extends RuntimeException {
    public MonoNoContentException(String message) {
        super(message);
    }
}
