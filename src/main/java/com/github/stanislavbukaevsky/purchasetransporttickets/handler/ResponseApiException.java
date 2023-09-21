package com.github.stanislavbukaevsky.purchasetransporttickets.handler;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseApiException {
    private Integer code;
    private String message;
    private LocalDateTime dateTime;

    public ResponseApiException(Integer code, String message, LocalDateTime dateTime) {
        this.code = code;
        this.message = message;
        this.dateTime = dateTime;
    }
}
