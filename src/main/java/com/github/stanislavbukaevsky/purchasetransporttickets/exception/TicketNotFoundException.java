package com.github.stanislavbukaevsky.purchasetransporttickets.exception;

/**
 * Класс-исключение для транспортных билетов. <br>
 * Наследуется от класса {@link RuntimeException}
 */
public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(String message) {
        super(message);
    }
}
