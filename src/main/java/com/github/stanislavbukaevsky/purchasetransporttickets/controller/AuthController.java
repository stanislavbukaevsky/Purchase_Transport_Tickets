package com.github.stanislavbukaevsky.purchasetransporttickets.controller;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RegistrationRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RegistrationResponseDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.REGISTRATION_MESSAGE_LOGGER_CONTROLLER;

@Slf4j
//@Validated
@RestController
@RequestMapping("/inputs")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<Mono<RegistrationResponseDto>> registration(@Valid @RequestBody RegistrationRequestDto registrationRequestDto) {
        Mono<RegistrationResponseDto> addUser = authService.registration(registrationRequestDto);
        log.info(REGISTRATION_MESSAGE_LOGGER_CONTROLLER, registrationRequestDto.getLogin());
        return ResponseEntity.ok(addUser);
    }
}
