package com.github.stanislavbukaevsky.purchasetransporttickets.constant;

/**
 * Этот класс содержит текстовые константные переменные для всех исключений в приложении
 */
public class ExceptionTextMessageConstant {
    public static final String REGISTRATION_MESSAGE_EXCEPTION_SERVICE = "Пользователь с таким логином уже существует! Попробуйте выбрать другой логин для регистрации";
    public static final String MONO_NO_CONTENT_EXCEPTION_MESSAGE_SERVICE = "Этот блок Mono оказался пустым, либо произошла ошибка на сервере!";
    public static final String AUTH_EXCEPTION_CONVERT_MESSAGE_SERVICE = "Внутренняя ошибка на сервере при авторизации! Проверьте правильность введенных данных";
    public static final String AUTH_EXCEPTION_AUTHENTICATION_MESSAGE_SERVICE = "Вы ввели неправильный логин или пароль! Попробуйте ввести другую комбинацию логина и пароля и повторите попытку";
    public static final String EXPIRED_JWT_EXCEPTION_MESSAGE_SERVICE = "Срок действия токена истек! Получите новый JWT токен и повторите попытку";
    public static final String UNSUPPORTED_JWT_EXCEPTION_MESSAGE_SERVICE = "Неподдерживаемый токен! Получите новый JWT токен и повторите попытку";
    public static final String MALFORMED_JWT_EXCEPTION_MESSAGE_SERVICE = "Токен неправильно сформирован! Получите новый JWT токен и повторите попытку";
    public static final String SIGNATURE_EXCEPTION_MESSAGE_SERVICE = "Подпись токена не действительна! Получите новый JWT токен и повторите попытку";
    public static final String EXCEPTION_MESSAGE_SERVICE = "Недопустимый токен! Получите новый JWT токен и повторите попытку";
    public static final String DATA_ACCESS_EXCEPTION_MESSAGE_REPOSITORY = "Произошла внутренняя ошибка на сервере! Попробуйте повторить действие. Входные параметры: ";
    public static final String DATA_ACCESS_EXCEPTION_FIND_MESSAGE_REPOSITORY = "Записи с таким запросом не существует в базе данных. Запрос искомой записи в базе данных: ";
    public static final String NULL_POINTER_EXCEPTION_MESSAGE_SERVICE = "Транспортных билетов с таким запросом несуществует! Попробуйте ввести другие входные параметры";
    public static final String NULL_POINTER_EXCEPTION_THREE_MESSAGE_SERVICE = "Транспортных маршрутов с таким запросом несуществует! Попробуйте ввести другие входные параметры";
    public static final String TICKET_NOT_FOUND_EXCEPTION_MESSAGE_SERVICE = "Транспортный билет уже продан и вы не можете его купить! Попробуйте выбрать другой билет для покупки";
}
