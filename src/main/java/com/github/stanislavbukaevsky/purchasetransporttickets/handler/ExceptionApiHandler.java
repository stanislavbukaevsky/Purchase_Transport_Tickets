package com.github.stanislavbukaevsky.purchasetransporttickets.handler;

import com.github.stanislavbukaevsky.purchasetransporttickets.exception.AuthException;
import com.github.stanislavbukaevsky.purchasetransporttickets.exception.LoginAlreadyExistsException;
import com.github.stanislavbukaevsky.purchasetransporttickets.exception.MonoNoContentException;
import com.github.stanislavbukaevsky.purchasetransporttickets.exception.TicketNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.ExceptionTextMessageConstant.MALFORMED_JWT_EXCEPTION_MESSAGE_SERVICE;

/**
 * Этот класс для обработки всех исключений приложения на уровне контроллеров
 */
@Slf4j
@RestControllerAdvice
public class ExceptionApiHandler {

    /**
     * Этот метод обрабатывает все исключения, возникшие с логином пользователя
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(LoginAlreadyExistsException.class)
    public ResponseEntity<ResponseApiException> loginAlreadyExistsException(LoginAlreadyExistsException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseApiException(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), getDateTime()));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с блоком Mono
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(MonoNoContentException.class)
    public ResponseEntity<ResponseApiException> monoNoContentException(MonoNoContentException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new ResponseApiException(HttpStatus.NO_CONTENT.value(), exception.getMessage(), getDateTime()));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с неправильной валидацией
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseValidationException> onConstraintValidationException(ConstraintViolationException exception) {
        final List<Violation> violations = exception.getConstraintViolations().stream()
                .map(violation ->
                        new Violation(violation.getPropertyPath().toString(), violation.getMessage(), getDateTime())
                ).collect(Collectors.toList());
        log.error(exception.getMessage(), exception);
        return ResponseEntity.ok(new ResponseValidationException(violations));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с неправильной валидацией
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseValidationException> onMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        final List<Violation> violations = exception.getBindingResult().getFieldErrors().stream()
                .map(violation ->
                        new Violation(violation.getField(), violation.getDefaultMessage(), getDateTime())
                ).collect(Collectors.toList());
        log.error(exception.getMessage(), exception);
        return ResponseEntity.ok(new ResponseValidationException(violations));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с неправильной валидацией
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ResponseValidationException> onWebExchangeBindException(WebExchangeBindException exception) {
        final List<Violation> violations = exception.getBindingResult().getFieldErrors().stream()
                .map(violation ->
                        new Violation(violation.getField(), violation.getDefaultMessage(), getDateTime())
                ).collect(Collectors.toList());
        log.error(exception.getMessage(), exception);
        return ResponseEntity.ok(new ResponseValidationException(violations));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с аутентификацией
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ResponseApiException> authException(AuthException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), getDateTime()));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с аутентификацией при помощи JWT
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseApiException> expiredJwtException(ExpiredJwtException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), getDateTime()));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с аутентификацией при помощи JWT
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ResponseApiException> unsupportedJwtException(UnsupportedJwtException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), getDateTime()));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с аутентификацией при помощи JWT
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ResponseApiException> malformedJwtException(MalformedJwtException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MALFORMED_JWT_EXCEPTION_MESSAGE_SERVICE, getDateTime()));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с аутентификацией при помощи JWT
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ResponseApiException> signatureException(SignatureException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), getDateTime()));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с маппингом
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(MappingException.class)
    public ResponseEntity<ResponseApiException> mappingException(MappingException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), getDateTime()));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с неправильным запросом к базе данных
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ResponseApiException> dataAccessException(DataAccessException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), getDateTime()));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с парсингом даты и времени
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ResponseApiException> dateTimeParseException(DateTimeParseException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), getDateTime()));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с транспортными билетами
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ResponseApiException> ticketNotFoundException(TicketNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), getDateTime()));
    }

    /**
     * Этот метод обрабатывает все исключения, возникшие с отсутствием запрашиваемого контента
     *
     * @param exception исключение
     * @return Возвращает сформированное сообщение пользователю об ошибке, возникшей в результате неправильного запроса
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseApiException> nullPointerException(NullPointerException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), getDateTime()));
    }

    /**
     * Приватный метод, который формирует настоящие дату и время
     *
     * @return Возвращает настоящие дату и время, когда было выброшено исключение
     */
    private LocalDateTime getDateTime() {
        return LocalDateTime.now();
    }
}
