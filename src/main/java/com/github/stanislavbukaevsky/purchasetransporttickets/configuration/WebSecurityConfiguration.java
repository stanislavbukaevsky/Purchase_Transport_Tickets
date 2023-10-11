package com.github.stanislavbukaevsky.purchasetransporttickets.configuration;

import com.github.stanislavbukaevsky.purchasetransporttickets.security.AuthenticationManager;
import com.github.stanislavbukaevsky.purchasetransporttickets.token.TokenAuthenticationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

/**
 * Конфигурационный класс для настройки Spring Security
 */
@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@EnableReactiveMethodSecurity
public class WebSecurityConfiguration {
    private final TokenAuthenticationConverter tokenAuthenticationConverter;
    private final AuthenticationManager authenticationManager;
    private static final String[] FREE_ACCESS = {
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/webjars/**",
            "/inputs/registration",
            "/inputs/authentication"
    };
    private static final String[] AUTHENTICATED_BUYER = {
            "/tickets/all-date-time",
            "/tickets/all-departure-point",
            "/tickets/all-destination",
            "/tickets/all-company-carrier",
            "/tickets/buying/**",
            "/tickets/buying-user-id"
    };
    private static final String[] ADMINISTRATOR_ACCESS = {
            "/carriers/add",
            "/carriers/update/**",
            "/carriers/delete/**",
            "/routes/add/**",
            "/routes/update/**",
            "/routes/delete/**",
            "/tickets/add",
            "/tickets/update/**",
            "/tickets/delete/**"
    };

    /**
     * Этот метод настраивает правила безопасности для работы с приложением и запрещает/разрешает доступ к определенным ресурсам
     *
     * @param http конфигурация http
     * @return Возвращает сконфигурированный и настроенный клиент http
     */
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authorizeExchange(authentication -> {
                    authentication.pathMatchers(FREE_ACCESS).permitAll();
                    authentication.pathMatchers(ADMINISTRATOR_ACCESS).hasAuthority("ADMINISTRATOR");
                    authentication.pathMatchers(AUTHENTICATED_BUYER).authenticated();
                })
                .addFilterAt(authenticationWebFilter(authenticationManager), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    /**
     * Приватный метод, который устанавливает стратегию аутентификации конкретного пользователя
     *
     * @param authenticationManager модуль проверки подлинности конкретного пользователя
     * @return Возвращает фильтр аутентификации для доступа к ресурсам
     */
    private AuthenticationWebFilter authenticationWebFilter(AuthenticationManager authenticationManager) {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(tokenAuthenticationConverter);
        authenticationWebFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/**"));
        return authenticationWebFilter;
    }
}
