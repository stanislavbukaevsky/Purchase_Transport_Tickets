package com.github.stanislavbukaevsky.purchasetransporttickets.mapper.record;

import com.github.stanislavbukaevsky.purchasetransporttickets.domain.tables.Tokens;
import com.github.stanislavbukaevsky.purchasetransporttickets.domain.tables.records.TokensRecord;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.Token;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;
import org.springframework.stereotype.Component;

/**
 * Класс-маппер, который преобразует информацию о JWT токенах в сущность. <br>
 * Этот класс расширяет интерфейс {@link RecordUnmapper}. Параметры: <br>
 * {@link Token} - модель JWT токена <br>
 * {@link TokensRecord} - сущность JWT токена
 */
@Component
@RequiredArgsConstructor
public class TokenRecordMapper implements RecordUnmapper<Token, TokensRecord> {
    private final DSLContext dsl;

    /**
     * Этот метод преобразует модель JWT токена в сущность
     *
     * @param token модель JWT токена
     * @return Возвращает сформированную сущность JWT токена
     * @throws MappingException исключение, возникающее при сбое запросов связанной службы сопоставления
     */
    @Override
    public @NotNull TokensRecord unmap(Token token) throws MappingException {
        TokensRecord tokensRecord = dsl.newRecord(Tokens.TOKENS, token);
        tokensRecord.setAccessToken(token.getAccessToken());
        tokensRecord.setExpiresAtAccess(token.getExpiresAtAccess());
        tokensRecord.setRefreshToken(token.getRefreshToken());
        tokensRecord.setExpiresAtRefresh(token.getExpiresAtRefresh());
        tokensRecord.setUserId(token.getUser().getId());
        return tokensRecord;
    }

    /**
     * Этот метод преобразует модель JWT токена в сущность.
     * Метод используется для изменения информации о JWT токенах
     *
     * @param token модель JWT токена
     * @return Возвращает сформированную сущность JWT токена
     */
    public @NotNull TokensRecord unmapUpdateToken(Token token) {
        TokensRecord tokensRecord = dsl.newRecord(Tokens.TOKENS, token);
        tokensRecord.setAccessToken(token.getAccessToken());
        tokensRecord.setExpiresAtAccess(token.getExpiresAtAccess());
        tokensRecord.setRefreshToken(token.getRefreshToken());
        tokensRecord.setExpiresAtRefresh(token.getExpiresAtRefresh());
        return tokensRecord;
    }
}
