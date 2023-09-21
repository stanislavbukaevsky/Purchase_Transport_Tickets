package com.github.stanislavbukaevsky.purchasetransporttickets.request;

import lombok.Data;

@Data
public class AuthUser {
    private String login;
    private String password;
}
