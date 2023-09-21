package com.github.stanislavbukaevsky.purchasetransporttickets.handler;

import com.github.stanislavbukaevsky.purchasetransporttickets.exception.LoginAlreadyExistsException;
import com.github.stanislavbukaevsky.purchasetransporttickets.exception.MonoNoContentException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(LoginAlreadyExistsException.class)
    public ResponseEntity<ResponseApiException> loginAlreadyExistsException(LoginAlreadyExistsException exception) {
        LocalDateTime dateTime = LocalDateTime.now();
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseApiException(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), dateTime));
    }

    @ExceptionHandler(MonoNoContentException.class)
    public ResponseEntity<ResponseApiException> monoNoContentException(MonoNoContentException exception) {
        LocalDateTime dateTime = LocalDateTime.now();
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new ResponseApiException(HttpStatus.NO_CONTENT.value(), exception.getMessage(), dateTime));
    }

    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
    public ResponseValidationException onConstraintValidationException(ConstraintViolationException exception) {
        final List<Violation> violations = exception.getConstraintViolations().stream()
                .map(violation ->
                        new Violation(violation.getPropertyPath().toString(), violation.getMessage())
                ).collect(Collectors.toList());
        log.info("sasasas");
        return new ResponseValidationException(violations);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
    public ResponseValidationException onMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        final List<Violation> violations = exception.getBindingResult().getFieldErrors().stream()
                .map(violation ->
                        new Violation(violation.getField(), violation.getDefaultMessage())
                ).collect(Collectors.toList());
        log.info("1111111111");
        return new ResponseValidationException(violations);
    }
}
