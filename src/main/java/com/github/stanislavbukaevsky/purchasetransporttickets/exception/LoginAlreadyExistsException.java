package com.github.stanislavbukaevsky.purchasetransporttickets.exception;

public class LoginAlreadyExistsException extends RuntimeException {
    public LoginAlreadyExistsException(String message) {
        super(message);
    }
}
