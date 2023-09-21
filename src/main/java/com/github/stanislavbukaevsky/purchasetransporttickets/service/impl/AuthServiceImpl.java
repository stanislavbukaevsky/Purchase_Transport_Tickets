package com.github.stanislavbukaevsky.purchasetransporttickets.service.impl;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RegistrationRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RegistrationResponseDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.exception.LoginAlreadyExistsException;
import com.github.stanislavbukaevsky.purchasetransporttickets.exception.MonoNoContentException;
import com.github.stanislavbukaevsky.purchasetransporttickets.jwt.TokenGenerateService;
import com.github.stanislavbukaevsky.purchasetransporttickets.mapping.UserMapping;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.Token;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.User;
import com.github.stanislavbukaevsky.purchasetransporttickets.repository.UserRepository;
import com.github.stanislavbukaevsky.purchasetransporttickets.request.AuthUser;
import com.github.stanislavbukaevsky.purchasetransporttickets.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.ExceptionTextMessageConstant.MONO_NO_CONTENT_EXCEPTION_MESSAGE_SERVICE;
import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.ExceptionTextMessageConstant.REGISTRATION_MESSAGE_EXCEPTION_SERVICE;
import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.REGISTRATION_MESSAGE_LOGGER_SERVICE;

@Slf4j
@Service
//@Validated
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    //    private final UserSecurityService userSecurityService;
    private final PasswordEncoder passwordEncoder;
    //    private final TokenDetailsService tokenDetailsService;
//    private final UserSecurity userSecurity;
    private final UserRepository userRepository;
    private final UserMapping userMapping;
    private final TokenGenerateService tokenGenerateService;

    @Override
    public Mono<RegistrationResponseDto> registration(RegistrationRequestDto registrationRequestDto) {
        Boolean existsUser = userRepository.existsUserByLogin(registrationRequestDto.getLogin());
        if (existsUser) {
            throw new LoginAlreadyExistsException(REGISTRATION_MESSAGE_EXCEPTION_SERVICE);
        }

        return Mono.just(userMapping.toUser(registrationRequestDto))
                .flatMap(user -> {
                    User saveUser = userRepository.save(user);
                    log.info(REGISTRATION_MESSAGE_LOGGER_SERVICE, saveUser.getLogin());
                    return Mono.just(userMapping.toRegistrationResponseDto(saveUser));
                }).switchIfEmpty(Mono.error(new MonoNoContentException(MONO_NO_CONTENT_EXCEPTION_MESSAGE_SERVICE)));
    }

    public Mono<Token> authorization(AuthUser authUser) {
        return Mono.just(userRepository.findUserByLogin(authUser.getLogin()))
                .flatMap(user -> {
                    if (passwordEncoder.matches(authUser.getPassword(), user.getPassword())) {
                        return Mono.just(tokenGenerateService.generateAccessAndRefresh(user));
                    }
                    return Mono.error(new RuntimeException("qqqqqqqqqq"));
                }).switchIfEmpty(Mono.error(new RuntimeException("65464565465645")));
//                return userSecurityService.findByUsername(login)
//                .flatMap(user -> {
//                    if (password.equals(user.getPassword()))
//                        return Mono.error(new RuntimeException("11111111"));
//                    return Mono.just(tokenGenerateService.generateAccessToken((UserSecurity) user));
//                }).switchIfEmpty(Mono.error(new RuntimeException("323232323232")));
    }
}
