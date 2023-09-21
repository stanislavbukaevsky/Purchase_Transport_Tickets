package com.github.stanislavbukaevsky.purchasetransporttickets.controller;

import com.github.stanislavbukaevsky.purchasetransporttickets.model.Token;
import com.github.stanislavbukaevsky.purchasetransporttickets.repository.UserRepository;
import com.github.stanislavbukaevsky.purchasetransporttickets.request.AuthUser;
import com.github.stanislavbukaevsky.purchasetransporttickets.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final AuthServiceImpl authService;

//    @PostMapping("/add-user")
//    public ResponseEntity<Mono<RegistrationResponseDto>> addUser(@RequestBody User user) {
//        Mono<RegistrationResponseDto> addUser = authService.registration(user);
//        return ResponseEntity.ok(addUser);
//    }

//    @GetMapping("/find")
//    public ResponseEntity<Mono<User>> findUserByLogin(@RequestParam String login) {
//        Mono<User> user = userRepository.findUserByLogin(login);
//        return ResponseEntity.ok(user);
//    }

    @PostMapping("/auth")
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Mono<Token>> auth(@RequestBody AuthUser authUser) {
        Mono<Token> token = authService.authorization(authUser);
        return ResponseEntity.ok(token);
    }
}
