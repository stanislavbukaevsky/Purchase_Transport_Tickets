package com.github.stanislavbukaevsky.purchasetransporttickets.repository;

import com.github.stanislavbukaevsky.purchasetransporttickets.domain.tables.Users;
import com.github.stanislavbukaevsky.purchasetransporttickets.mapper.UserRecordMapper;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.User;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final DSLContext dsl;
    private final UserRecordMapper userRecordMapper;


    public User save(User user) {
        return dsl.insertInto(Users.USERS)
                .set(userRecordMapper.unmap(user))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Ошибка при регистрации: " + user.getId()))
                .into(User.class);
    }

    public User findUserByLogin(String login) {
        return dsl.selectFrom(Users.USERS)
                .where(Users.USERS.LOGIN.eq(login))
                .fetchAny()
//                .fetchOptional()
//                .orElseThrow(() -> new RuntimeException("Такого пользователя нет: " + login))
                .into(User.class);
    }

    public Mono<User> findById(Long id) {
        return Mono.just(dsl.selectFrom(Users.USERS)
                .where(Users.USERS.ID.eq(id))
                .fetchOptional()
                .orElseThrow(() -> new RuntimeException("Такого пользователя нет: " + id))
                .into(User.class));
    }

    public Boolean existsUserByLogin(String login) {
        return dsl.fetchExists(dsl.selectOne()
                .from(Users.USERS)
                .where(Users.USERS.LOGIN.eq(login)));

    }
}
