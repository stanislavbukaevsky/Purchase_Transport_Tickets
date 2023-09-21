package com.github.stanislavbukaevsky.purchasetransporttickets.dto;

import com.github.stanislavbukaevsky.purchasetransporttickets.enums.Role;
import lombok.Data;

@Data
public class RegistrationResponseDto {
    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private Role role;
}
