package com.github.stanislavbukaevsky.purchasetransporttickets.mapping;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RegistrationRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RegistrationResponseDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapping {
    RegistrationRequestDto toRegistrationDto(User user);

    RegistrationResponseDto toRegistrationResponseDto(User user);

    RegistrationResponseDto toRegistrationResponseDtoTwo(RegistrationRequestDto registrationRequestDto);

    User toUser(RegistrationResponseDto registrationResponseDto);

    User toUser(RegistrationRequestDto registrationRequestDto);
}
