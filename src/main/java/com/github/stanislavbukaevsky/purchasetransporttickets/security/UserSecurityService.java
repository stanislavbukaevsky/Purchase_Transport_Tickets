package com.github.stanislavbukaevsky.purchasetransporttickets.security;

import com.github.stanislavbukaevsky.purchasetransporttickets.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserSecurityService implements ReactiveUserDetailsService {
    private final UserRepository userRepository;

    @Override
//    @Transactional
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.just(userRepository.findUserByLogin(username))
                .cast(UserDetails.class);
    }
}
