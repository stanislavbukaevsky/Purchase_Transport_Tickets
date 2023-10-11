package com.github.stanislavbukaevsky.purchasetransporttickets.token;

import com.github.stanislavbukaevsky.purchasetransporttickets.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.ExceptionTextMessageConstant.*;

/**
 * Класс для генерации и валидации access и refresh токенов
 */
@Slf4j
@Component
public class TokenDetailsService {
    private final SecretKey secretAccess;
    private final SecretKey secretRefresh;
    private final String issuer;

    public TokenDetailsService(@Value("${jwt.secret.access}") String secretAccess,
                               @Value("${jwt.secret.refresh}") String secretRefresh,
                               @Value("${jwt.user.issuer}") String issuer) {
        this.secretAccess = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretAccess));
        this.secretRefresh = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretRefresh));
        this.issuer = issuer;
    }

    /**
     * Этот метод генерирует access токен
     *
     * @param user модель пользователя
     * @return Возвращает сгенерированный JWT access токен в строковом виде
     */
    public String generateAccessToken(@NonNull User user) {
        final String role = "role";
        final String login = "login";
        final Date issuedAt = getIssuedAt();
        final Date accessExpiration = getAccessExpiration();
        final Map<String, Object> claims = new HashMap<>() {{
            put(role, user.getRole());
            put(login, user.getLogin());
        }};

        return Jwts.builder()
                .setIssuer(issuer)
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setSubject(user.getLogin())
                .setIssuedAt(issuedAt)
                .setExpiration(accessExpiration)
                .signWith(secretAccess)
                .compact();
    }

    /**
     * Этот метод генерирует refresh токен
     *
     * @param user модель пользователя
     * @return Возвращает сгенерированный JWT refresh токен в строковом виде
     */
    public String generateRefreshToken(@NonNull User user) {
        final Date issuedAt = getIssuedAt();
        final Date refreshExpiration = getRefreshExpiration();

        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(user.getLogin())
                .setIssuedAt(issuedAt)
                .setExpiration(refreshExpiration)
                .signWith(secretRefresh).compact();
    }

    /**
     * Этот метод генерирует дату истечения срока действия access токена
     *
     * @return Возвращает дату истечения срока действия access токена
     */
    public Date getAccessExpiration() {
        final LocalDateTime dateTime = LocalDateTime.now();
        final long validityTimeAccessToken = 60;
        final Instant accessExpirationInstant = dateTime.plusMinutes(validityTimeAccessToken).atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(accessExpirationInstant);
    }

    /**
     * Этот метод генерирует дату истечения срока действия refresh токена
     *
     * @return Возвращает дату истечения срока действия refresh токена
     */
    public Date getRefreshExpiration() {
        final LocalDateTime dateTime = LocalDateTime.now();
        final long validityTimeRefreshToken = 30;
        final Instant refreshExpirationInstant = dateTime.plusDays(validityTimeRefreshToken).atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(refreshExpirationInstant);
    }

    /**
     * Этот метод отвечает за проверку валидности access токена
     *
     * @param accessToken access токен
     */
    public void validateAccessToken(@NonNull String accessToken) {
        validateToken(accessToken, secretAccess);
    }

    /**
     * Этот метод отвечает за проверку валидности refresh токена
     *
     * @param refreshToken refresh токен
     * @return Возвращает true или false, в зависимости от того валиден refresh токен или нет
     */
    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, secretRefresh);
    }

    /**
     * Этот метод вытягивает из access токена данные о пользователе
     *
     * @param accessToken access токен
     * @return Возвращает полученные данные о пользователе по его access токену
     */
    public Claims getAccessClaims(@NonNull String accessToken) {
        return getClaims(accessToken, secretAccess);
    }

    /**
     * Этот метод вытягивает из refresh токена данные о пользователе
     *
     * @param refreshToken refresh токен
     * @return Возвращает полученные данные о пользователе по его refresh токену
     */
    public Claims getRefreshClaims(@NonNull String refreshToken) {
        return getClaims(refreshToken, secretRefresh);
    }

    /**
     * Приватный метод для генерации валидации токенов
     *
     * @param token  токен
     * @param secret секретный ключ
     * @return Возвращает true или false, в зависимости от того валиден токен или нет
     */
    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error(EXPIRED_JWT_EXCEPTION_MESSAGE_SERVICE, expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error(UNSUPPORTED_JWT_EXCEPTION_MESSAGE_SERVICE, unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error(MALFORMED_JWT_EXCEPTION_MESSAGE_SERVICE, mjEx);
        } catch (SignatureException sEx) {
            log.error(SIGNATURE_EXCEPTION_MESSAGE_SERVICE, sEx);
        } catch (Exception e) {
            log.error(EXCEPTION_MESSAGE_SERVICE, e);
        }
        return false;
    }

    /**
     * Приватный метод для формирования пользовательских данных по токену
     *
     * @param token  токен
     * @param secret секретный ключ
     * @return Возвращает сформированные данные о пользователе по его токену
     */
    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Приватный метод для конвертации даты и времени в старый формат
     *
     * @return Возвращает сконвертированную дату и время в устаревшем формате
     */
    private Date getIssuedAt() {
        final LocalDateTime dateTime = LocalDateTime.now();
        return Timestamp.valueOf(dateTime);
    }
}
