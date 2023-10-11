package com.github.stanislavbukaevsky.purchasetransporttickets.token;

import com.github.stanislavbukaevsky.purchasetransporttickets.exception.AuthException;
import com.github.stanislavbukaevsky.purchasetransporttickets.security.CustomPrincipal;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.ExceptionTextMessageConstant.AUTH_EXCEPTION_CONVERT_MESSAGE_SERVICE;
import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.CONVERT_MESSAGE_LOGGER_SERVICE;

/**
 * Этот класс используется для конвертации из ServerWebExchange в Authentication
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationConverter implements ServerAuthenticationConverter {
    private final TokenDetailsService tokenDetailsService;
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int BEGIN_INDEX = 7;

    /**
     * Этот метод конвертирует ServerWebExchange в Authentication и разрешает аутентификацию
     *
     * @param exchange объект запроса от пользователя
     * @return Возвращает объект аутентификации обернутый в Mono
     */
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return getTokenFromRequest(exchange).flatMap(token -> {
            if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
                String processedToken = token.substring(BEGIN_INDEX);
                tokenDetailsService.validateAccessToken(processedToken);
                log.info(CONVERT_MESSAGE_LOGGER_SERVICE);
                return getAuthentication(processedToken);
            }
            return Mono.error(new AuthException(AUTH_EXCEPTION_CONVERT_MESSAGE_SERVICE));
        });
    }

    /**
     * Приватный метод для получения токена по запросу пользователя
     *
     * @param exchange объект запроса от пользователя
     * @return Возвращает токен в строковом формате
     */
    private Mono<String> getTokenFromRequest(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest()
                .getHeaders()
                .getFirst(AUTHORIZATION));
    }

    /**
     * Приватный метод для аутентфикации пользователя по его токену
     *
     * @param token токен пользователя
     * @return Возвращает объект аутентификации обернутый в Mono
     */
    private Mono<Authentication> getAuthentication(String token) {
        Claims claims = tokenDetailsService.getAccessClaims(token);
        String role = claims.get("role", String.class);
        String login = claims.get("login", String.class);
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        CustomPrincipal customPrincipal = new CustomPrincipal(login);
        return Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(customPrincipal, null, authorities));
    }
}
