//package com.github.stanislavbukaevsky.purchasetransporttickets.jwt;
//
//import com.github.stanislavbukaevsky.purchasetransporttickets.exception.UnauthorizedException;
//import com.github.stanislavbukaevsky.purchasetransporttickets.model.User;
//import com.github.stanislavbukaevsky.purchasetransporttickets.security.UserSecurity;
//import com.github.stanislavbukaevsky.purchasetransporttickets.security.UserSecurityService;
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import io.jsonwebtoken.security.SignatureException;
//import lombok.NonNull;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//import reactor.core.scheduler.Schedulers;
//
//import javax.crypto.SecretKey;
//import java.security.Key;
//import java.time.Duration;
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.temporal.ChronoUnit;
//import java.util.Collection;
//import java.util.Date;
//import java.util.Optional;
//
//@Slf4j
//@Component
//public class TokenDetailsService {
//    private final SecretKey jwtAccessSecret;
//    private final SecretKey jwtRefreshSecret;
//    private final UserSecurityService userSecurityService;
//
//    public TokenDetailsService(@Value("${jwt.secret.access}") String jwtAccessSecret,
//                               @Value("${jwt.secret.refresh}") String jwtRefreshSecret,
//                               UserSecurityService userSecurityService) {
//        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
//        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
//        this.userSecurityService = userSecurityService;
//    }
//
//    /**
//     * Этот метод генерирует access токен
//     *
//     * @param userSecurity пользовательские данные
//     * @return Возвращает сгенерированный JWT access токен
//     */
//    public String generateAccessToken(@NonNull UserSecurity userSecurity) {
//        final LocalDateTime createdDate = LocalDateTime.now();
//        final Instant accessExpirationInstant = createdDate.plusMinutes(60).atZone(ZoneId.systemDefault()).toInstant();
//        final Date accessExpiration = Date.from(accessExpirationInstant);
//
//        return Jwts.builder()
//                .setSubject(userSecurity.getUsername())
//                .setExpiration(accessExpiration)
//                .signWith(jwtAccessSecret)
//                .claim("role", userSecurity.getUsersRecord().getRole())
//                .claim("login", userSecurity.getUsername())
//                .compact();
//    }
//
//    /**
//     * Этот метод генерирует refresh токен
//     *
//     * @param userSecurity пользовательские данные
//     * @return Возвращает сгенерированный JWT refresh токен
//     */
//    public String generateRefreshToken(@NonNull UserSecurity userSecurity) {
//        final LocalDateTime createdDate = LocalDateTime.now();
//        final Instant refreshExpirationInstant = createdDate.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
//        final Date refreshExpiration = Date.from(refreshExpirationInstant);
//
//        return Jwts.builder()
//                .setSubject(userSecurity.getUsername())
//                .setExpiration(refreshExpiration)
//                .signWith(jwtRefreshSecret)
//                .compact();
//    }
//
//    /**
//     * Этот метод отвечает за проверку валидности access токена
//     *
//     * @param accessToken access токен
//     * @return Возвращает true или false, в зависимости от того валиден access токен или нет
//     */
//    public boolean validateAccessToken(@NonNull String accessToken) {
//        return validateToken(accessToken, jwtAccessSecret);
//    }
//
//    /**
//     * Этот метод отвечает за проверку валидности refresh токена
//     *
//     * @param refreshToken refresh токен
//     * @return Возвращает true или false, в зависимости от того валиден refresh токен или нет
//     */
//    public boolean validateRefreshToken(@NonNull String refreshToken) {
//        return validateToken(refreshToken, jwtRefreshSecret);
//    }
//
//    /**
//     * Этот метод формирует из access токена данные о пользователе
//     *
//     * @param accessToken access токен
//     * @return Возвращает сгенерированные данные о пользователе по его access токену
//     */
//    public Mono<Claims> getAccessClaims(@NonNull String accessToken) {
//        return Mono.just(getClaims(accessToken, jwtAccessSecret))
//                .onErrorResume(e -> Mono.error(new UnauthorizedException("Неверный токен! " + e.getMessage())));
//    }
//
//    /**
//     * Этот метод формирует из refresh токена данные о пользователе
//     *
//     * @param refreshToken refresh токен
//     * @return Возвращает сгенерированные данные о пользователе по его refresh токену
//     */
//    public Mono<Claims> getRefreshClaims(@NonNull String refreshToken) {
//        return Mono.just(getClaims(refreshToken, jwtRefreshSecret))
//                .onErrorResume(e -> Mono.error(new UnauthorizedException("Неверный токен! " + e.getMessage())));
//    }
//
//    public Authentication getAuthentication(String token) {
//        Claims claims = Jwts
//                .parserBuilder()
//                .setSigningKey(jwtAccessSecret)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//        Object authenticationClaims = claims.get("role");
//        Collection<? extends GrantedAuthority> authorities = authenticationClaims == null
//                ? AuthorityUtils.NO_AUTHORITIES
//                : AuthorityUtils.commaSeparatedStringToAuthorityList(authenticationClaims.toString());
//
//        Optional<UserDetails> userSecurity = Optional.ofNullable(userSecurityService.findByUsername(claims.getSubject())
//                .blockOptional().orElseThrow(() -> new RuntimeException("Пользователя не существует!")));
//
//        return new UsernamePasswordAuthenticationToken(userSecurity, token, authorities);
//    }
//
//    /**
//     * Приватный метод для проверки валидности токенов
//     *
//     * @param token  токен
//     * @param secret секретный ключ
//     * @return Возвращает true или false, в зависимости от того валиден токен или нет
//     */
//    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(secret)
//                    .build()
//                    .parseClaimsJws(token);
//            return true;
//        } catch (ExpiredJwtException expEx) {
//            log.error("Срок действия токена истек!", expEx);
//        } catch (UnsupportedJwtException unsEx) {
//            log.error("Неподдерживаемый токен!", unsEx);
//        } catch (MalformedJwtException mjEx) {
//            log.error("Неправильно сформированный токен!", mjEx);
//        } catch (SignatureException sEx) {
//            log.error("Недействительная подпись!", sEx);
//        } catch (Exception e) {
//            log.error("Недопустимый токен!", e);
//        }
//        return false;
//    }
//
//    /**
//     * Приватный метод для получения пользовательских данных по токену
//     *
//     * @param token  токен
//     * @param secret секретный ключ
//     * @return Возвращает сгенерированные данные о пользователе по его токену
//     */
//    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
//        return Jwts.parserBuilder()
//                .setSigningKey(secret)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//}
