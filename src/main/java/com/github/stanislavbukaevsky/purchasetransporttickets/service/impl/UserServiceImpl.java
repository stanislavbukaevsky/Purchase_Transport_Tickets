package com.github.stanislavbukaevsky.purchasetransporttickets.service.impl;

import com.github.stanislavbukaevsky.purchasetransporttickets.model.User;
import com.github.stanislavbukaevsky.purchasetransporttickets.repository.UserRepository;
import com.github.stanislavbukaevsky.purchasetransporttickets.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.FIND_USER_BY_LOGIN_MESSAGE_LOGGER_SERVICE;

/**
 * Сервис-класс с бизнес-логикой для пользователя в приложении.
 * Реализует интерфейс {@link UserService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * Реализация метода для поиска зарегистрированных пользователей в приложении
     *
     * @param login логин пользователя
     * @return Возвращает найденного пользователя обернутого в Mono
     */
    @Override
    public Mono<User> findUserByLogin(String login) {
        log.info(FIND_USER_BY_LOGIN_MESSAGE_LOGGER_SERVICE, login);
        return Mono.just(userRepository.findUserByLogin(login));
    }
}
