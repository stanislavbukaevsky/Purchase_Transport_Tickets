//package com.github.stanislavbukaevsky.purchasetransporttickets.jwt;
//
//import com.github.stanislavbukaevsky.purchasetransporttickets.security.UserSecurityService;
//import io.jsonwebtoken.Claims;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//import reactor.core.scheduler.Schedulers;
//
//import java.io.IOException;
//
//@Component
//@RequiredArgsConstructor
//public class TokenDetailsFilter implements WebFilter {
//    private final TokenDetailsService tokenDetailsService;
//    private static final String AUTHORIZATION = "Authorization";
//    private static final String BEARER_PREFIX = "Bearer ";
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        final String token = getTokenFromRequest(exchange.getRequest());
//        if (StringUtils.hasText(token) && tokenDetailsService.validateAccessToken(token)) {
//            return Mono.fromCallable(() -> tokenDetailsService.getAuthentication(token))
//                    .subscribeOn(Schedulers.boundedElastic())
//                    .flatMap(authentication -> {
//                        return chain.filter(exchange)
//                                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
//                    });
//        }
//        return chain.filter(exchange);
//    }
//
//    private String getTokenFromRequest(ServerHttpRequest request) {
//        final String bearer = request.getHeaders().getFirst(AUTHORIZATION);
//        if (StringUtils.hasText(bearer) && bearer.startsWith(BEARER_PREFIX)) {
//            return bearer.substring(7);
//        }
//        return null;
//    }
//}
