package com.github.stanislavbukaevsky.purchasetransporttickets.mapper;

import com.github.stanislavbukaevsky.purchasetransporttickets.domain.tables.Users;
import com.github.stanislavbukaevsky.purchasetransporttickets.domain.tables.records.UsersRecord;
import com.github.stanislavbukaevsky.purchasetransporttickets.enums.Role;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.User;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRecordMapper implements RecordUnmapper<User, UsersRecord> {
    private final DSLContext dsl;
    private final PasswordEncoder passwordEncoder;

    @Override
    public @NotNull UsersRecord unmap(User user) throws MappingException {
        UsersRecord usersRecord = dsl.newRecord(Users.USERS, user);
        usersRecord.setLogin(user.getLogin());
        usersRecord.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRecord.setFirstName(user.getFirstName());
        usersRecord.setMiddleName(user.getMiddleName());
        usersRecord.setLastName(user.getLastName());
        usersRecord.setRole(Role.BUYER.name());
        return usersRecord;
    }
}
