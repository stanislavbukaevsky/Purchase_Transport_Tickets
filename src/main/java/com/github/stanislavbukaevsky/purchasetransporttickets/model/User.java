package com.github.stanislavbukaevsky.purchasetransporttickets.model;

import com.github.stanislavbukaevsky.purchasetransporttickets.enums.Role;
import lombok.Data;

import java.io.Serializable;

/**
 * Модель представления пользователей
 */
@Data
public class User implements Serializable {
    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private Role role;
}
