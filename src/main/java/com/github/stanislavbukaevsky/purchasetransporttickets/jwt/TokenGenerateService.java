package com.github.stanislavbukaevsky.purchasetransporttickets.jwt;

import com.github.stanislavbukaevsky.purchasetransporttickets.model.Token;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenGenerateService {
    private final SecretKey secretAccess;
    private final SecretKey secretRefresh;
    private final String issuer;

    public TokenGenerateService(@Value("${jwt.secret.access}") String secretAccess,
                                @Value("${jwt.secret.refresh}") String secretRefresh,
                                @Value("${jwt.user.issuer}") String issuer) {
        this.secretAccess = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretAccess));
        this.secretRefresh = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretRefresh));
        this.issuer = issuer;
    }

    public Token generateAccessToken(User user) {
        final LocalDateTime createdDate = LocalDateTime.now();
        final Date date = Timestamp.valueOf(createdDate);
        final Instant accessExpirationInstant = createdDate.plusMinutes(60).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        final Map<String, Object> claims = new HashMap<>() {{
            put("role", user.getRole());
            put("login", user.getLogin());
        }};
        final String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setSubject(user.getLogin())
                .setIssuedAt(date)
                .setExpiration(accessExpiration)
                .signWith(secretAccess)
                .compact();

        return Token.builder()
                .id(user.getId())
                .accessToken(accessToken)
                .user(user)
                .issuedAt(date)
                .expiresAtAccess(accessExpiration)
                .build();
    }

    public Token generateRefreshToken(User user) {
        final LocalDateTime createdDate = LocalDateTime.now();
        final Date date = Timestamp.valueOf(createdDate);
        final Instant refreshExpirationInstant = createdDate.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        final String refreshToken = Jwts.builder()
                .setIssuer(issuer)
                .setSubject(user.getLogin())
                .setIssuedAt(date)
                .setExpiration(refreshExpiration)
                .signWith(secretRefresh)
                .compact();

        return Token.builder()
                .id(user.getId())
                .refreshToken(refreshToken)
                .user(user)
                .issuedAt(date)
                .expiresAtRefresh(refreshExpiration)
                .build();
    }

    public Token generateAccessAndRefresh(User user) {
        final LocalDateTime createdDate = LocalDateTime.now();
        final Date date = Timestamp.valueOf(createdDate);
        final Instant accessExpirationInstant = createdDate.plusMinutes(60).atZone(ZoneId.systemDefault()).toInstant();
        final Instant refreshExpirationInstant = createdDate.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        final Map<String, Object> claims = new HashMap<>() {{
            put("role", user.getRole());
            put("login", user.getLogin());
        }};
        final String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setSubject(user.getLogin())
                .setIssuedAt(date)
                .setExpiration(accessExpiration)
                .signWith(secretAccess)
                .compact();
        final String refreshToken = Jwts.builder()
                .setIssuer(issuer)
                .setSubject(user.getLogin())
                .setIssuedAt(date)
                .setExpiration(refreshExpiration)
                .signWith(secretRefresh)
                .compact();

        return Token.builder()
                .id(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(user)
                .issuedAt(date)
                .expiresAtAccess(accessExpiration)
                .expiresAtRefresh(refreshExpiration)
                .build();
    }


}
