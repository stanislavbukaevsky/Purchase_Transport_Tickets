package com.github.stanislavbukaevsky.purchasetransporttickets.repository;

import com.github.stanislavbukaevsky.purchasetransporttickets.domain.tables.Users;
import com.github.stanislavbukaevsky.purchasetransporttickets.mapper.record.UserRecordMapper;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.ExceptionTextMessageConstant.DATA_ACCESS_EXCEPTION_FIND_MESSAGE_REPOSITORY;
import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.ExceptionTextMessageConstant.DATA_ACCESS_EXCEPTION_MESSAGE_REPOSITORY;
import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.*;

/**
 * Класс-репозиторий, для связи с базой данных и вытягивания из нее информации о пользователе
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final DSLContext dsl;
    private final UserRecordMapper userRecordMapper;

    /**
     * Этот метод сохраняет информацию о пользователе в базу данных
     *
     * @param user модель пользователя
     * @return Возвращает модель пользователя
     */
    public User save(User user) {
        log.info(SAVE_USER_MESSAGE_LOGGER_REPOSITORY, user);
        return dsl.insertInto(Users.USERS)
                .set(userRecordMapper.unmap(user))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException(DATA_ACCESS_EXCEPTION_MESSAGE_REPOSITORY + user))
                .into(User.class);
    }

    /**
     * Этот метод ищет пользователя в базе данных по его уникальному логину
     *
     * @param login уникальный логин пользователя
     * @return Возвращает модель найденного пользователя
     */
    public User findUserByLogin(String login) {
        log.info(FIND_USER_BY_LOGIN_MESSAGE_LOGGER_REPOSITORY, login);
        return dsl.selectFrom(Users.USERS)
                .where(Users.USERS.LOGIN.eq(login))
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException(DATA_ACCESS_EXCEPTION_FIND_MESSAGE_REPOSITORY + login))
                .into(User.class);
    }

    /**
     * Этот метод проверяет присутствие пользователя в базе данных по его уникальному логину
     *
     * @param login уникальный логин пользователя
     * @return Возвращает true, если пользователь с таким логином присутствует в базе данных
     */
    public Boolean existsUserByLogin(String login) {
        log.info(EXISTS_USER_BY_LOGIN_MESSAGE_LOGGER_REPOSITORY, login);
        return dsl.fetchExists(dsl.selectOne()
                .from(Users.USERS)
                .where(Users.USERS.LOGIN.eq(login)));

    }
}
