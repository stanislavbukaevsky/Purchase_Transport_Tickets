package com.github.stanislavbukaevsky.purchasetransporttickets.mapper;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RouteRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RouteResponseDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.Route;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер-интерфейс, который преобразует информацию о маршруте в DTO
 */
@Mapper
public interface RouteMapper {
    /**
     * Этот метод преобразует полученные поля из DTO в модель, для получения информации о маршруте
     *
     * @param routeRequestDto DTO запроса с информацией о маршруте
     * @return Возвращает сформированную модель с информацией о маршруте
     */
    Route toRouteModel(RouteRequestDto routeRequestDto);

    /**
     * Этот метод преобразует полученные поля из модели в DTO, для получения информации о маршруте. <br>
     * Используется аннотация {@link Mapping} для соответствия полей
     *
     * @param route модель с информацией о маршруте
     * @return Возвращает сформированную DTO с ответом о маршруте
     */
    @Mapping(source = "route.carrier.id", target = "carrierId")
    @Mapping(source = "route.carrier.companyName", target = "companyName")
    @Mapping(source = "route.carrier.phoneNumber", target = "phoneNumber")
    RouteResponseDto toRouteResponseDto(Route route);
}
