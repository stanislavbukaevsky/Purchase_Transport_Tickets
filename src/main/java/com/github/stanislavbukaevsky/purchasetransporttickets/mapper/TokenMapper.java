package com.github.stanislavbukaevsky.purchasetransporttickets.mapper;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.InfoAuthenticationUserDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.enums.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

/**
 * Маппер-интерфейс, который преобразует информацию о JWT токенах в DTO
 */
@Mapper
public interface TokenMapper {
    /**
     * Этот метод преобразует полученные поля в DTO, для получения информации об аутентифицированном пользователе. <br>
     * Используется аннотация {@link Mapping} для соответствия полей
     *
     * @param id               уникальный идентификатор пользователя
     * @param login            логин пользователя
     * @param password         пароль пользователя
     * @param firstName        имя пользователя
     * @param middleName       отчество пользователя
     * @param lastName         фамилия пользователя
     * @param role             роль пользователя
     * @param accessToken      access токен пользователя
     * @param refreshToken     refresh токен пользователя
     * @param expiresAtAccess  дата истечения срока действия access токена
     * @param expiresAtRefresh дата истечения срока действия refresh токена
     * @return Возвращает сформированную DTO с ответом об аутентифицированном пользователе
     */
    @Mapping(source = "id", target = "userId")
    InfoAuthenticationUserDto toInfoAuthenticationUserDto(Long id,
                                                          String login,
                                                          String password,
                                                          String firstName,
                                                          String middleName,
                                                          String lastName,
                                                          Role role,
                                                          String accessToken,
                                                          String refreshToken,
                                                          LocalDateTime expiresAtAccess,
                                                          LocalDateTime expiresAtRefresh);
}
