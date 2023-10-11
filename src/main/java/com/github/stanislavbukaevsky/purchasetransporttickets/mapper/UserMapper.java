package com.github.stanislavbukaevsky.purchasetransporttickets.mapper;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RegistrationRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RegistrationResponseDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.enums.Role;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер-интерфейс, который преобразует информацию о пользователе в DTO
 */
@Mapper
public interface UserMapper {
    /**
     * Этот метод преобразует полученные поля из DTO в модель, для получения информации о пользователе
     *
     * @param registrationRequestDto DTO запроса с информацией о пользователе
     * @return Возвращает сформированную модель с информацией о пользователе
     */
    User toUserModel(RegistrationRequestDto registrationRequestDto);

    /**
     * Этот метод преобразует полученные поля из модели в DTO, для получения информации о пользователе. <br>
     * Используется аннотация {@link Mapping} для соответствия полей
     *
     * @param user модель с информацией о пользователе
     * @return Возвращает сформированную DTO с ответом о пользователе
     */
    @Mapping(source = "user.role", target = "role")
    RegistrationResponseDto toRegistrationResponseDto(User user);

    /**
     * Этот метод дает описание роли пользователя
     *
     * @param role роль пользователя
     * @return Возвращает описание на русском языке о роли пользователя в приложении
     */
    default String toRoleDescription(Role role) {
        return role.getDescription();
    }
}
