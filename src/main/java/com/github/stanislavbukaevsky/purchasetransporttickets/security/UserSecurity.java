package com.github.stanislavbukaevsky.purchasetransporttickets.security;

import com.github.stanislavbukaevsky.purchasetransporttickets.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Collection;
import java.util.List;

@Component
@Getter
@Setter
@Slf4j
@RequestScope
@RequiredArgsConstructor
public class UserSecurity implements UserDetails {
    private User usersRecord;

    @Override
    public String getUsername() {
        return usersRecord.getLogin();
    }

    @Override
    public String getPassword() {
        return usersRecord.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(usersRecord.getRole().name()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
