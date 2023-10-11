package com.github.stanislavbukaevsky.purchasetransporttickets.constant;

/**
 * Этот класс содержит текстовые константные переменные для всех логов в приложении
 */
public class LoggerTextMessageConstant {
    // Логи для методов в контроллерах
    public static final String REGISTRATION_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для регистрации нового пользователя в контроллере. Логин зарегистрированного пользователя: {}";
    public static final String AUTHENTICATION_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для аутентификации зарегистрированного пользователя в контроллере. Логин пользователя: {}";
    public static final String ADD_CARRIER_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для добавления нового транспортного перевозчика на платформу в контроллере. Запрос от пользователя: {}";
    public static final String UPDATE_CARRIER_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для изменения информации о транспортном перевозчике на платформе в контроллере. Запрос от пользователя: {}. Идентификатор транспортного перевозчика: {}";
    public static final String DELETE_CARRIER_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для удаления транспортного перевозчика с платформы в контроллере. Идентификатор транспортного перевозчика: {}";
    public static final String ADD_ROUTE_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для добавления нового транспортного маршрута на платформу в контроллере. Запрос от пользователя: {}. Идентификатор транспортного перевозчика: {}";
    public static final String UPDATE_ROUTE_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для изменения информации о транспортном маршруте на платформе в контроллере. Запрос от пользователя: {}. Идентификатор транспортного маршрута: {}";
    public static final String DELETE_ROUTE_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для удаления транспортного маршрута с платформы в контроллере. Идентификатор транспортного маршрута: {}";
    public static final String ADD_TICKET_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для добавления нового транспортного билета на платформу в контроллере. Запрос от пользователя: {}";
    public static final String UPDATE_TICKET_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для изменения информации о транспортном билете на платформе в контроллере. Запрос от пользователя: {}. Идентификатор транспортного билета: {}";
    public static final String DELETE_TICKET_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для удаления транспортного билета с платформы в контроллере. Идентификатор транспортного билета: {}";
    public static final String GET_TICKETS_BY_DATE_AND_TIME_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для просмотра отсортированного списка транспортных билетов по дате и времени на платформе в контроллере. Запрос от пользователя: {}. Номер страницы: {}. Количество записей на странице: {}";
    public static final String GET_TICKETS_BY_DEPARTURE_POINT_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для просмотра отсортированного списка транспортных билетов по пункту отправления на платформе в контроллере. Запрос от пользователя: {}. Номер страницы: {}. Количество записей на странице: {}";
    public static final String GET_TICKETS_BY_DESTINATION_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для просмотра отсортированного списка транспортных билетов по пункту назначения на платформе в контроллере. Запрос от пользователя: {}. Номер страницы: {}. Количество записей на странице: {}";
    public static final String GET_TICKETS_BY_COMPANY_NAME_CARRIER_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для просмотра отсортированного списка транспортных билетов по названию компании перевозчика на платформе в контроллере. Запрос от пользователя: {}. Номер страницы: {}. Количество записей на странице: {}";
    public static final String BUYING_TICKET_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для покупки транспортного билета зарегистрированному пользователю на платформе в контроллере. Уникальный идентификатор билета: {}";
    public static final String FIND_BUYING_TICKETS_BY_USER_ID_MESSAGE_LOGGER_CONTROLLER = "Вызван метод для просмотра отсортированного списка купленных транспортных билетов пользователем на платформе в контроллере. Номер страницы: {}. Количество записей на странице: {}";

    // Логи для методов в сервисах
    public static final String AUTHENTICATION_MANAGER_MESSAGE_LOGGER_SERVICE = "Вызван метод проверки на существование пользователя в базе данных и является ли верными его учётные данные в сервисе. Объект аутентификации: {}";
    public static final String PASSWORD_ENCODER_ENCODE_MESSAGE_LOGGER_SERVICE = "Вызван метод шифрования пароля пользователя. Пароль пользователя: {}";
    public static final String PASSWORD_ENCODER_MATCHES_MESSAGE_LOGGER_SERVICE = "Вызван метод проверки пароля пользователя. Полученный пароль от пользователя: {}. Зашифрованный пароль, сохраненный в базе данных: {}";
    public static final String REGISTRATION_MESSAGE_LOGGER_SERVICE = "Вызван метод для регистрации нового пользователя в сервисе. Логин зарегистрированного пользователя: {}";
    public static final String CONVERT_MESSAGE_LOGGER_SERVICE = "Вызван метод авторизации пользователя на платформе";
    public static final String AUTHENTICATION_MESSAGE_LOGGER_SERVICE = "Вызван метод для аутентификации зарегистрированного пользователя в сервисе. Логин пользователя: {}";
    public static final String GENERATING_INFO_AUTHENTICATION_USER_DTO_MESSAGE_LOGGER_SERVICE = "Вызван приватный метод для генерации ответа с личной информацией о пользователе в сервисе. Логин пользователя: {}";
    public static final String ADD_CARRIER_MESSAGE_LOGGER_SERVICE = "Вызван метод для добавления нового транспортного перевозчика на платформу в сервисе. Запрос от пользователя: {}";
    public static final String UPDATE_CARRIER_MESSAGE_LOGGER_SERVICE = "Вызван метод для изменения информации о транспортном перевозчике на платформе в сервисе. Запрос от пользователя: {}. Идентификатор транспортного перевозчика: {}";
    public static final String DELETE_CARRIER_MESSAGE_LOGGER_SERVICE = "Вызван метод для удаления транспортного перевозчика с платформы в сервисе. Идентификатор транспортного перевозчика: {}";
    public static final String ADD_ROUTE_MESSAGE_LOGGER_SERVICE = "Вызван метод для добавления нового транспортного маршрута на платформу в сервисе. Запрос от пользователя: {}. Идентификатор транспортного перевозчика: {}";
    public static final String UPDATE_ROUTE_MESSAGE_LOGGER_SERVICE = "Вызван метод для изменения информации о транспортном маршруте на платформе в сервисе. Запрос от пользователя: {}. Идентификатор транспортного маршрута: {}";
    public static final String DELETE_ROUTE_MESSAGE_LOGGER_SERVICE = "Вызван метод для удаления транспортного маршрута с платформы в сервисе. Идентификатор транспортного маршрута: {}";
    public static final String ADD_TICKET_MESSAGE_LOGGER_SERVICE = "Вызван метод для добавления нового транспортного билета на платформу в сервисе. Запрос от пользователя: {}";
    public static final String UPDATE_TICKET_MESSAGE_LOGGER_SERVICE = "Вызван метод для изменения информации о транспортном билете на платформе в сервисе. Запрос от пользователя: {}. Идентификатор транспортного билета: {}";
    public static final String DELETE_TICKET_MESSAGE_LOGGER_SERVICE = "Вызван метод для удаления транспортного билета с платформы в сервисе. Идентификатор транспортного билета: {}";
    public static final String PARSE_DATE_AND_TIME_MESSAGE_LOGGER_SERVICE = "Вызван приватный метод для преобразования даты и времени в строковом виде к формату LocalDateTime в сервисе. Полученные дата и время от пользователя: {}";
    public static final String FORMING_TICKET_RESPONSE_DTO_MESSAGE_LOGGER_SERVICE = "Вызван приватный метод для генерации ответа с информацией о транспортном билете";
    public static final String FORMING_BUYING_TICKET_RESPONSE_DTO_MESSAGE_LOGGER_SERVICE = "Вызван приватный метод для генерации ответа с информацией о купленном транспортном билете";
    public static final String FORMING_TICKETS_RESPONSE_DTO_MESSAGE_LOGGER_SERVICE = "Вызван приватный метод для генерации списка ответов с информацией о транспортных билетах";
    public static final String ADD_TOKEN_MESSAGE_LOGGER_SERVICE = "Вызван метод для добавления нового токена на платформу в сервисе";
    public static final String FIND_USER_BY_LOGIN_MESSAGE_LOGGER_SERVICE = "Вызван метод для поиска зарегистрированных пользователей в приложении. Логин пользователя: {}";
    public static final String GET_TICKETS_BY_DATE_AND_TIME_MESSAGE_LOGGER_SERVICE = "Вызван метод для просмотра отсортированного списка транспортных билетов по дате и времени на платформе в сервисе. Запрос от пользователя: {}. Номер страницы: {}. Количество записей на странице: {}";
    public static final String GET_TICKETS_BY_DEPARTURE_POINT_MESSAGE_LOGGER_SERVICE = "Вызван метод для просмотра отсортированного списка транспортных билетов по пункту отправления на платформе в сервисе. Запрос от пользователя: {}. Номер страницы: {}. Количество записей на странице: {}";
    public static final String GET_TICKETS_BY_DESTINATION_MESSAGE_LOGGER_SERVICE = "Вызван метод для просмотра отсортированного списка транспортных билетов по пункту назначения на платформе в сервисе. Запрос от пользователя: {}. Номер страницы: {}. Количество записей на странице: {}";
    public static final String GET_TICKETS_BY_COMPANY_NAME_CARRIER_MESSAGE_LOGGER_SERVICE = "Вызван метод для просмотра отсортированного списка транспортных билетов по названию компании перевозчика на платформе в сервисе. Запрос от пользователя: {}. Номер страницы: {}. Количество записей на странице: {}";
    public static final String BUYING_TICKET_MESSAGE_LOGGER_SERVICE = "Вызван метод для покупки транспортного билета зарегистрированному пользователю на платформе в сервисе. Уникальный идентификатор билета: {}";
    public static final String FIND_BUYING_TICKETS_BY_USER_ID_MESSAGE_LOGGER_SERVICE = "Вызван метод для просмотра отсортированного списка купленных транспортных билетов пользователем на платформе в сервисе. Номер страницы: {}. Количество записей на странице: {}";
    public static final String PUBLISH_MESSAGE_LOGGER_SERVICE = "Вызван метод для отправки текстовых сообщений в топик Redis в сервисе. Новое сообщение: {}";
    public static final String ON_MESSAGE_LISTENER_MESSAGE_LOGGER_SERVICE = "Вызван метод для получения текстовых сообщений из топика Redis в сервисе. Полученное сообщение: {}";
    public static final String KAFKA_SEND_MESSAGE_LOGGER_SERVICE = "Вызван метод для отправки сообщений в топик Kafka в сервисе. Запрос от пользователя: {}";
    public static final String REPLACE_ACCESS_TOKEN_MESSAGE_LOGGER_SERVICE = "Автоматически вызван метод обновления access токена в сервисе. Уникальный идентификатор токена: {}";
    public static final String REPLACE_REFRESH_TOKEN_MESSAGE_LOGGER_SERVICE = "Автоматически вызван метод обновления access и refresh токенов в сервисе. Уникальный идентификатор токена: {}";

    // Логи для методов в репозиториях
    public static final String SAVE_CARRIER_MESSAGE_LOGGER_REPOSITORY = "Вызван метод сохранения нового перевозчика в базу данных. Запрос от пользователя: {}";
    public static final String UPDATE_CARRIER_MESSAGE_LOGGER_REPOSITORY = "Вызван метод изменения и сохранения информации о перевозчике в базе данных. Запрос от пользователя: {}";
    public static final String FIND_CARRIER_BY_ID_MESSAGE_LOGGER_REPOSITORY = "Вызван метод поиска перевозчика по уникальному идентификатору в базе данных. Уникальный идентификатор перевозчика: {}";
    public static final String FIND_CARRIER_BY_COMPANY_NAME_MESSAGE_LOGGER_REPOSITORY = "Вызван метод поиска перевозчика по названию компании в базе данных. Название компании перевозчика: {}";
    public static final String DELETE_CARRIER_BY_ID_MESSAGE_LOGGER_REPOSITORY = "Вызван метод удаления перевозчика по уникальному идентификатору из базы данных. Уникальный идентификатор перевозчика: {}";
    public static final String SAVE_ROUTE_MESSAGE_LOGGER_REPOSITORY = "Вызван метод сохранения нового маршрута в базу данных. Запрос от пользователя: {}";
    public static final String UPDATE_ROUTE_MESSAGE_LOGGER_REPOSITORY = "Вызван метод изменения и сохранения информации о маршруте в базе данных. Запрос от пользователя: {}";
    public static final String FIND_ROUTE_BY_ID_MESSAGE_LOGGER_REPOSITORY = "Вызван метод поиска маршрута по уникальному идентификатору в базе данных. Уникальный идентификатор маршрута: {}";
    public static final String FIND_CUSTOM_ROUTE_BY_ID_MESSAGE_LOGGER_REPOSITORY = "Вызван метод поиска маршрута с заданными и искомыми полями по уникальному идентификатору в базе данных. Уникальный идентификатор маршрута: {}";
    public static final String DELETE_ROUTE_BY_ID_MESSAGE_LOGGER_REPOSITORY = "Вызван метод удаления маршрута по уникальному идентификатору из базы данных. Уникальный идентификатор маршрута: {}";
    public static final String SAVE_TICKET_MESSAGE_LOGGER_REPOSITORY = "Вызван метод сохранения нового билета в базу данных. Запрос от пользователя: {}";
    public static final String UPDATE_TICKET_MESSAGE_LOGGER_REPOSITORY = "Вызван метод изменения и сохранения информации о билете в базе данных. Запрос от пользователя: {}";
    public static final String FIND_TICKET_BY_ID_MESSAGE_LOGGER_REPOSITORY = "Вызван метод поиска билета по уникальному идентификатору в базе данных. Уникальный идентификатор билета: {}";
    public static final String DELETE_TICKET_BY_ID_MESSAGE_LOGGER_REPOSITORY = "Вызван метод удаления билета по уникальному идентификатору из базы данных. Уникальный идентификатор билета: {}";
    public static final String SAVE_TOKEN_MESSAGE_LOGGER_REPOSITORY = "Вызван метод сохранения нового токена в базу данных. Запрос от пользователя: {}";
    public static final String UPDATE_TOKEN_MESSAGE_LOGGER_REPOSITORY = "Вызван метод изменения и сохранения информации о токене в базе данных. Запрос от пользователя: {}";
    public static final String FIND_TOKEN_BY_USER_ID_MESSAGE_LOGGER_REPOSITORY = "Вызван метод поиска токена по уникальному идентификатору пользователя в базе данных. Уникальный идентификатор пользователя: {}";
    public static final String EXISTS_TOKEN_BY_USER_ID_MESSAGE_LOGGER_REPOSITORY = "Вызван метод проверки присутствия токена по уникальному идентификатору пользователя в базе данных. Уникальный идентификатор пользователя: {}";
    public static final String SAVE_USER_MESSAGE_LOGGER_REPOSITORY = "Вызван метод сохранения нового пользователя в базу данных. Запрос от пользователя: {}";
    public static final String FIND_USER_BY_LOGIN_MESSAGE_LOGGER_REPOSITORY = "Вызван метод поиска пользователя по его уникальному логину в базе данных. Логин пользователя: {}";
    public static final String EXISTS_USER_BY_LOGIN_MESSAGE_LOGGER_REPOSITORY = "Вызван метод проверки присутствия пользователя по его уникальному логину в базе данных. Логин пользователя: {}";
    public static final String FIND_ROUTES_BY_DEPARTURE_POINT_MESSAGE_LOGGER_REPOSITORY = "Вызван метод поиска списка маршрутов по пункту отправления в базе данных. Пункт отправления: {}";
    public static final String FIND_ROUTES_BY_DESTINATION_MESSAGE_LOGGER_REPOSITORY = "Вызван метод поиска списка маршрутов по пункту назначения в базе данных. Пункт назначения: {}";
    public static final String FIND_ROUTES_BY_COMPANY_NAME_CARRIER_MESSAGE_LOGGER_REPOSITORY = "Вызван метод поиска списка маршрутов по названию компании перевозчика в базе данных. Уникальный идентификатор названия компании перевозчика: {}";
    public static final String FIND_TICKETS_BY_DATE_AND_TIME_DEPARTURE_MESSAGE_LOGGER_REPOSITORY = "Вызван метод поиска списка билетов по дате и времени отправления в базе данных. Дата и время отправления: {}";
    public static final String FIND_TICKETS_BY_ROUTE_ID_MESSAGE_LOGGER_REPOSITORY = "Вызван метод поиска списка билетов по уникальному идентификатору маршрута в базе данных. Уникальный идентификатор маршрута: {}";
    public static final String FIND_TICKETS_BY_USER_ID_MESSAGE_LOGGER_REPOSITORY = "Вызван метод поиска списка билетов по уникальному идентификатору пользователя в базе данных. Уникальный идентификатор пользователя: {}";
    public static final String REDIS_SAVE_MESSAGE_LOGGER_REPOSITORY = "Вызван метод сохранения информации о билете в харанилище Redis. Запрос от пользователя: {}";
    public static final String REDIS_DELETE_MESSAGE_LOGGER_REPOSITORY = "Вызван метод удаления информации о билете из харанилища Redis. Уникальный идентификатор билета: {}";
    public static final String REDIS_FIND_ALL_TICKETS_MESSAGE_LOGGER_REPOSITORY = "Вызван метод поиска билетов в харанилище Redis";
    public static final String FIND_TOKENS_BY_EXPIRES_AT_ACCESS_MESSAGE_LOGGER_REPOSITORY = "Вызван метод поиска списка токенов по дате и времени окончания действия access токена в базе данных. Дата и время: {}";
    public static final String FIND_TOKENS_BY_EXPIRES_AT_REFRESH_MESSAGE_LOGGER_REPOSITORY = "Вызван метод поиска списка токенов по дате и времени окончания действия refresh токена в базе данных. Дата и время: {}";
    public static final String EXISTS_TOKENS_BY_EXPIRES_AT_ACCESS_MESSAGE_LOGGER_REPOSITORY = "Вызван метод проверки присутствия списка токенов по дате и времени окончания действия access токена в базе данных. Дата и время: {}";
    public static final String EXISTS_TOKENS_BY_EXPIRES_AT_REFRESH_MESSAGE_LOGGER_REPOSITORY = "Вызван метод проверки присутствия списка токенов по дате и времени окончания действия refresh токена в базе данных. Дата и время: {}";
}
