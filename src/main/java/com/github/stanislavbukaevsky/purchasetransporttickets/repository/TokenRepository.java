package com.github.stanislavbukaevsky.purchasetransporttickets.repository;

import com.github.stanislavbukaevsky.purchasetransporttickets.domain.tables.Tokens;
import com.github.stanislavbukaevsky.purchasetransporttickets.mapper.record.TokenRecordMapper;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.ExceptionTextMessageConstant.DATA_ACCESS_EXCEPTION_FIND_MESSAGE_REPOSITORY;
import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.ExceptionTextMessageConstant.DATA_ACCESS_EXCEPTION_MESSAGE_REPOSITORY;
import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.*;

/**
 * Класс-репозиторий, для связи с базой данных и вытягивания из нее информации о токенах
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class TokenRepository {
    private final DSLContext dsl;
    private final TokenRecordMapper tokenRecordMapper;

    /**
     * Этот метод сохраняет информацию о токенах в базу данных
     *
     * @param token модель токена
     * @return Возвращает модель токена
     */
    public Token save(Token token) {
        log.info(SAVE_TOKEN_MESSAGE_LOGGER_REPOSITORY, token);
        return dsl.insertInto(Tokens.TOKENS)
                .set(tokenRecordMapper.unmap(token))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException(DATA_ACCESS_EXCEPTION_MESSAGE_REPOSITORY + token))
                .into(Token.class);
    }

    /**
     * Этот метод изменяет и сохраняет информацию о токене в базе данных
     *
     * @param token модель токена
     * @return Возвращает модель токена
     */
    public Token update(Token token) {
        log.info(UPDATE_TOKEN_MESSAGE_LOGGER_REPOSITORY, token);
        return dsl.update(Tokens.TOKENS)
                .set(tokenRecordMapper.unmapUpdateToken(token))
                .where(Tokens.TOKENS.USER_ID.eq(token.getUserId()))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException(DATA_ACCESS_EXCEPTION_MESSAGE_REPOSITORY + token))
                .into(Token.class);
    }

    /**
     * Этот метод ищет токен в базе данных по уникальному идентификатору пользователя
     *
     * @param id уникальный идентификатор пользователя
     * @return Возвращает модель найденного токена
     */
    public Token findTokenByUserId(Long id) {
        log.info(FIND_TOKEN_BY_USER_ID_MESSAGE_LOGGER_REPOSITORY, id);
        return dsl.select()
                .from(Tokens.TOKENS)
                .where(Tokens.TOKENS.USER_ID.eq(id))
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException(DATA_ACCESS_EXCEPTION_FIND_MESSAGE_REPOSITORY + id))
                .into(Token.class);
    }

    /**
     * Этот метод ищет список токенов в базе данных по дате и времени окончания действия access токена
     *
     * @param dateTime дата и время
     * @return Возвращает список найденных токенов
     */
    public List<Token> findTokensByExpiresAtAccess(LocalDateTime dateTime) {
        log.info(FIND_TOKENS_BY_EXPIRES_AT_ACCESS_MESSAGE_LOGGER_REPOSITORY, dateTime);
        return dsl.select()
                .from(Tokens.TOKENS)
                .where(Tokens.TOKENS.EXPIRES_AT_ACCESS.eq(dateTime))
                .orderBy(Tokens.TOKENS.ID.asc())
                .fetchInto(Token.class);
    }

    /**
     * Этот метод ищет список токенов в базе данных по дате и времени окончания действия refresh токена
     *
     * @param dateTime дата и время
     * @return Возвращает список найденных токенов
     */
    public List<Token> findTokensByExpiresAtRefresh(LocalDateTime dateTime) {
        log.info(FIND_TOKENS_BY_EXPIRES_AT_REFRESH_MESSAGE_LOGGER_REPOSITORY, dateTime);
        return dsl.select()
                .from(Tokens.TOKENS)
                .where(Tokens.TOKENS.EXPIRES_AT_REFRESH.eq(dateTime))
                .orderBy(Tokens.TOKENS.ID.asc())
                .fetchInto(Token.class);
    }

    /**
     * Этот метод проверяет присутствие токена в базе данных по уникальному идентификатору пользователя
     *
     * @param id уникальный идентификатор пользователя
     * @return Возвращает true, если пользователь с таким идентификатором присутствует в базе данных токенов
     */
    public Boolean existsTokenByUserId(Long id) {
        log.info(EXISTS_TOKEN_BY_USER_ID_MESSAGE_LOGGER_REPOSITORY, id);
        return dsl.fetchExists(dsl.selectOne()
                .from(Tokens.TOKENS)
                .where(Tokens.TOKENS.USER_ID.eq(id)));
    }

    /**
     * Этот метод проверяет присутствие списка токенов в базе данных по дате и времени окончания действия access токена
     *
     * @param dateTime дата и время
     * @return Возвращает true, если список токенов присутствует в базе данных
     */
    public List<Boolean> existsTokensByExpiresAtAccess(LocalDateTime dateTime) {
        log.info(EXISTS_TOKENS_BY_EXPIRES_AT_ACCESS_MESSAGE_LOGGER_REPOSITORY, dateTime);
        return Collections.singletonList(dsl.fetchExists(dsl.selectOne()
                .from(Tokens.TOKENS)
                .where(Tokens.TOKENS.EXPIRES_AT_ACCESS.eq(dateTime))));
    }

    /**
     * Этот метод проверяет присутствие списка токенов в базе данных по дате и времени окончания действия refresh токена
     *
     * @param dateTime дата и время
     * @return Возвращает true, если список токенов присутствует в базе данных
     */
    public List<Boolean> existsTokensByExpiresAtRefresh(LocalDateTime dateTime) {
        log.info(EXISTS_TOKENS_BY_EXPIRES_AT_REFRESH_MESSAGE_LOGGER_REPOSITORY, dateTime);
        return Collections.singletonList(dsl.fetchExists(dsl.selectOne()
                .from(Tokens.TOKENS)
                .where(Tokens.TOKENS.EXPIRES_AT_REFRESH.eq(dateTime))));
    }
}
