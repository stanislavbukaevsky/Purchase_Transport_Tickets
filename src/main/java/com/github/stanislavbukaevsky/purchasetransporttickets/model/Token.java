package com.github.stanislavbukaevsky.purchasetransporttickets.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder(toBuilder = true)
public class Token {
    private Long id;
    private String accessToken;
    private String refreshToken;
    private User user;
    private Date issuedAt;
    private Date expiresAtAccess;
    private Date expiresAtRefresh;
}
