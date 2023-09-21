package com.github.stanislavbukaevsky.purchasetransporttickets.service;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RegistrationRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RegistrationResponseDto;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<RegistrationResponseDto> registration(RegistrationRequestDto registrationRequestDto);
}
