package com.github.stanislavbukaevsky.purchasetransporttickets.configuration;

//import com.github.stanislavbukaevsky.purchasetransporttickets.jwt.TokenDetailsFilter;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
//@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
//    private final TokenDetailsFilter tokenDetailsFilter;
//    @Value("${jwt.secret}")
//    private String secret;

//    @Bean
//    public SecurityWebFilterChain filterChain(ServerHttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
//        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
//                .authorizeExchange(auth ->
//                        auth
//                                .pathMatchers("/users/add-user").permitAll()
//                                .pathMatchers("/users/find").authenticated()
//                )
//                .addFilterAt(bearerAuthenticationFilter(authenticationManager), SecurityWebFiltersOrder.AUTHENTICATION);
//
//        return http.build();
//
//    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authorizeExchange(authentication -> {
                    authentication.pathMatchers("/users/add-user", "/users/auth", "/inputs/registration").permitAll();
                    authentication.pathMatchers("/users/find").authenticated();
                })
//                .addFilterAt(tokenDetailsFilter, SecurityWebFiltersOrder.HTTP_BASIC)
                .build();
    }

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(12);
//    }
}
