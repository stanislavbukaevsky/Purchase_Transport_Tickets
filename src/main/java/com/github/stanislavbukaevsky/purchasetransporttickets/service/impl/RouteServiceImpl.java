package com.github.stanislavbukaevsky.purchasetransporttickets.service.impl;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RouteRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RouteResponseDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.RouteUpdateRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.mapper.RouteMapper;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.Carrier;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.Route;
import com.github.stanislavbukaevsky.purchasetransporttickets.repository.CarrierRepository;
import com.github.stanislavbukaevsky.purchasetransporttickets.repository.RouteRepository;
import com.github.stanislavbukaevsky.purchasetransporttickets.service.RouteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.*;

/**
 * Сервис-класс с бизнес-логикой для маршрута в приложении.
 * Реализует интерфейс {@link RouteService}
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final CarrierRepository carrierRepository;
    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;

    /**
     * Реализация метода для добавления новых маршрутов в приложении.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param routeRequestDto объект DTO с запросом от пользователя
     * @param id              уникальный идентификатор перевозчика
     * @return Возвращает DTO с информацией о добавленном маршруте
     */
    @Override
    public RouteResponseDto addRoute(@Valid RouteRequestDto routeRequestDto, @Positive Long id) {
        Carrier carrier = carrierRepository.findCarrierById(id);
        Route route = routeMapper.toRouteModel(routeRequestDto);
        route.setCarrier(carrier);
        Route result = routeRepository.save(route);

        RouteResponseDto routeResponseDto = routeMapper.toRouteResponseDto(result);
        routeResponseDto.setCarrierId(carrier.getId());
        routeResponseDto.setCompanyName(carrier.getCompanyName());
        routeResponseDto.setPhoneNumber(carrier.getPhoneNumber());
        log.info(ADD_ROUTE_MESSAGE_LOGGER_SERVICE, routeRequestDto, id);
        return routeResponseDto;
    }

    /**
     * Реализация метода для изменения информации о маршруте в приложении.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param routeUpdateRequestDto объект DTO с запросом от пользователя
     * @param id                    уникальный идентификатор маршрута
     * @return Возвращает DTO с информацией об измененном маршруте
     */
    @Override
    public RouteResponseDto updateRoute(@Valid RouteUpdateRequestDto routeUpdateRequestDto, @Positive Long id) {
        Carrier carrier = carrierRepository.findCarrierByCompanyName(routeUpdateRequestDto.getCompanyNameCarrier());
        Route route = routeRepository.findRouteById(id);
        route.setDeparturePoint(routeUpdateRequestDto.getDeparturePoint());
        route.setDestination(routeUpdateRequestDto.getDestination());
        route.setCarrier(carrier);
        route.setDurationInMinutes(routeUpdateRequestDto.getDurationInMinutes());
        Route result = routeRepository.update(route);

        RouteResponseDto routeResponseDto = routeMapper.toRouteResponseDto(result);
        routeResponseDto.setCarrierId(carrier.getId());
        routeResponseDto.setCompanyName(carrier.getCompanyName());
        routeResponseDto.setPhoneNumber(carrier.getPhoneNumber());
        log.info(UPDATE_ROUTE_MESSAGE_LOGGER_SERVICE, routeUpdateRequestDto, id);
        return routeResponseDto;
    }

    /**
     * Реализация метода для удаления маршрута из базы данных.
     * Этот метод доступен только для пользователей с ролью Администратор
     *
     * @param id уникальный идентификатор маршрута
     */
    @Override
    public void deleteRoute(@Positive Long id) {
        Route route = routeRepository.findRouteById(id);
        routeRepository.deleteById(route.getId());
        log.info(DELETE_ROUTE_MESSAGE_LOGGER_SERVICE, id);
    }
}
