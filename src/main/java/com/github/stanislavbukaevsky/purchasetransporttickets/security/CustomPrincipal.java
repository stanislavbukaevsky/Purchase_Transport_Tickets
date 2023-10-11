package com.github.stanislavbukaevsky.purchasetransporttickets.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;

/**
 * Этот класс для текущего залогиненного пользователя.
 * Реализует интерфейс {@link Principal}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomPrincipal implements Principal {
    private String login;

    @Override
    public String getName() {
        return login;
    }
}
