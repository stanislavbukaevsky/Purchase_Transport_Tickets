package com.github.stanislavbukaevsky.purchasetransporttickets.service.impl;

import com.github.stanislavbukaevsky.purchasetransporttickets.model.Token;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.User;
import com.github.stanislavbukaevsky.purchasetransporttickets.repository.TokenRepository;
import com.github.stanislavbukaevsky.purchasetransporttickets.repository.UserRepository;
import com.github.stanislavbukaevsky.purchasetransporttickets.service.TokenService;
import com.github.stanislavbukaevsky.purchasetransporttickets.token.TokenDetailsService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.*;

/**
 * Сервис-класс с бизнес-логикой для JWT токенов в приложении.
 * Реализует интерфейс {@link TokenService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final TokenDetailsService tokenDetailsService;

    /**
     * Реализация метода для добавления новых JWT токенов в приложении
     *
     * @param userId           уникальный идентификатор пользователя
     * @param accessToken      access токен
     * @param refreshToken     refresh токен
     * @param user             модель пользователя
     * @param expiresAtAccess  дата истечения срока действия access токена
     * @param expiresAtRefresh дата истечения срока действия refresh токена
     */
    @Override
    public void addToken(Long userId,
                         String accessToken,
                         String refreshToken,
                         User user,
                         LocalDateTime expiresAtAccess,
                         LocalDateTime expiresAtRefresh) {
        Token token = new Token();
        token.setUserId(userId);
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setUser(user);
        token.setExpiresAtAccess(expiresAtAccess);
        token.setExpiresAtRefresh(expiresAtRefresh);
        tokenRepository.save(token);
        log.info(ADD_TOKEN_MESSAGE_LOGGER_SERVICE);
    }

    /**
     * Реализация метода для выдачи нового access-токена для зарегистрированного пользователя на платформе.
     * Этот метод отрабатывает автоматически каждую минуту
     */
    @Scheduled(cron = "${scheduled.time.from.access-token}")
    public void replaceAccessToken() {
        LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<Boolean> existsTokens = tokenRepository.existsTokensByExpiresAtAccess(dateTime);

        for (Boolean existsToken : existsTokens) {
            if (existsToken) {
                List<Token> tokens = tokenRepository.findTokensByExpiresAtAccess(dateTime);
                for (Token token : tokens) {
                    String refreshToken = token.getRefreshToken();
                    boolean validateToken = tokenDetailsService.validateRefreshToken(refreshToken);
                    if (validateToken) {
                        Claims claims = tokenDetailsService.getRefreshClaims(refreshToken);
                        String login = claims.getSubject();
                        User user = userRepository.findUserByLogin(login);
                        String accessToken = tokenDetailsService.generateAccessToken(user);
                        LocalDateTime expiresAtAccess = LocalDateTime.ofInstant(
                                        tokenDetailsService.getAccessExpiration().toInstant(), ZoneId.systemDefault())
                                .truncatedTo(ChronoUnit.MINUTES);
                        token.setAccessToken(accessToken);
                        token.setExpiresAtAccess(expiresAtAccess);
                        tokenRepository.update(token);
                        log.info(REPLACE_ACCESS_TOKEN_MESSAGE_LOGGER_SERVICE, token.getId());
                    }
                }
            }
        }
    }

    /**
     * Реализация метода для выдачи новых access и refresh токенов для зарегистрированного пользователя на платформе.
     * Этот метод отрабатывает автоматически каждый день в 12:00
     */
    @Scheduled(cron = "${scheduled.time.from.refresh-token}")
    public void replaceRefreshToken() {
        LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<Boolean> existsTokens = tokenRepository.existsTokensByExpiresAtRefresh(dateTime);

        for (Boolean existsToken : existsTokens) {
            if (existsToken) {
                List<Token> tokens = tokenRepository.findTokensByExpiresAtRefresh(dateTime);
                for (Token token : tokens) {
                    String refreshToken = token.getRefreshToken();
                    boolean validateToken = tokenDetailsService.validateRefreshToken(refreshToken);
                    if (validateToken) {
                        Claims claims = tokenDetailsService.getRefreshClaims(refreshToken);
                        String login = claims.getSubject();
                        User user = userRepository.findUserByLogin(login);
                        String accessToken = tokenDetailsService.generateAccessToken(user);
                        String newRefreshToken = tokenDetailsService.generateRefreshToken(user);
                        LocalDateTime expiresAtAccess = LocalDateTime.ofInstant(
                                        tokenDetailsService.getAccessExpiration().toInstant(), ZoneId.systemDefault())
                                .truncatedTo(ChronoUnit.MINUTES);
                        LocalDateTime expiresAtRefresh = LocalDateTime.ofInstant(
                                        tokenDetailsService.getRefreshExpiration().toInstant(), ZoneId.systemDefault())
                                .truncatedTo(ChronoUnit.MINUTES);
                        token.setAccessToken(accessToken);
                        token.setExpiresAtAccess(expiresAtAccess);
                        token.setRefreshToken(newRefreshToken);
                        token.setExpiresAtRefresh(expiresAtRefresh);
                        tokenRepository.update(token);
                        log.info(REPLACE_REFRESH_TOKEN_MESSAGE_LOGGER_SERVICE, token.getId());
                    }
                }
            }
        }
    }
}
