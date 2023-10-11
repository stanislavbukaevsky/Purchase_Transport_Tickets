package com.github.stanislavbukaevsky.purchasetransporttickets.service;

import com.github.stanislavbukaevsky.purchasetransporttickets.model.User;
import reactor.core.publisher.Mono;

/**
 * Сервис-интерфейс с методами для пользователя в приложении.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface UserService {
    /**
     * Сигнатура метода для поиска зарегистрированных пользователей в приложении
     *
     * @param login логин пользователя
     * @return Возвращает найденного пользователя обернутого в Mono
     */
    Mono<User> findUserByLogin(String login);
}
