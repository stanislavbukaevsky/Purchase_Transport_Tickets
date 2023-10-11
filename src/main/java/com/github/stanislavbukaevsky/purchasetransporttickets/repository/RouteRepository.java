package com.github.stanislavbukaevsky.purchasetransporttickets.repository;

import com.github.stanislavbukaevsky.purchasetransporttickets.domain.tables.Routes;
import com.github.stanislavbukaevsky.purchasetransporttickets.mapper.record.RouteRecordMapper;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.Route;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.ExceptionTextMessageConstant.DATA_ACCESS_EXCEPTION_FIND_MESSAGE_REPOSITORY;
import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.ExceptionTextMessageConstant.DATA_ACCESS_EXCEPTION_MESSAGE_REPOSITORY;
import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.*;

/**
 * Класс-репозиторий, для связи с базой данных и вытягивания из нее информации о маршруте
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class RouteRepository {
    private final DSLContext dsl;
    private final RouteRecordMapper routeRecordMapper;

    /**
     * Этот метод сохраняет информацию о маршруте в базу данных
     *
     * @param route модель маршрута
     * @return Возвращает модель маршрута
     */
    public Route save(Route route) {
        log.info(SAVE_ROUTE_MESSAGE_LOGGER_REPOSITORY, route);
        return dsl.insertInto(Routes.ROUTES)
                .set(routeRecordMapper.unmap(route))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException(DATA_ACCESS_EXCEPTION_MESSAGE_REPOSITORY + route))
                .into(Route.class);
    }

    /**
     * Этот метод изменяет и сохраняет информацию о маршруте в базе данных
     *
     * @param route модель маршрута
     * @return Возвращает модель маршрута
     */
    public Route update(Route route) {
        log.info(UPDATE_ROUTE_MESSAGE_LOGGER_REPOSITORY, route);
        return dsl.update(Routes.ROUTES)
                .set(routeRecordMapper.unmap(route))
                .where(Routes.ROUTES.ID.eq(route.getId()))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException(DATA_ACCESS_EXCEPTION_MESSAGE_REPOSITORY + route))
                .into(Route.class);
    }

    /**
     * Этот метод ищет маршрут в базе данных по его уникальному идентификатору
     *
     * @param id уникальный идентификатор маршрута
     * @return Возвращает модель найденного маршрута
     */
    public Route findRouteById(Long id) {
        log.info(FIND_ROUTE_BY_ID_MESSAGE_LOGGER_REPOSITORY, id);
        return dsl.select()
                .from(Routes.ROUTES)
                .where(Routes.ROUTES.ID.eq(id))
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException(DATA_ACCESS_EXCEPTION_FIND_MESSAGE_REPOSITORY + id))
                .into(Route.class);
    }

    /**
     * Этот метод ищет маршрут в базе данных с заданными и искомыми полями по его уникальному идентификатору
     *
     * @param id уникальный идентификатор маршрута
     * @return Возвращает модель найденного маршрута
     */
    public Route findCustomRouteById(Long id) {
        log.info(FIND_CUSTOM_ROUTE_BY_ID_MESSAGE_LOGGER_REPOSITORY, id);
        return dsl.select(Routes.ROUTES.ID, Routes.ROUTES.DEPARTURE_POINT,
                        Routes.ROUTES.DESTINATION, Routes.ROUTES.CARRIER_ID,
                        Routes.ROUTES.DURATION_IN_MINUTES)
                .from(Routes.ROUTES)
                .where(Routes.ROUTES.ID.eq(id))
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException(DATA_ACCESS_EXCEPTION_FIND_MESSAGE_REPOSITORY + id))
                .into(Route.class);
    }

    /**
     * Этот метод удаляет маршрут из базы данных по его уникальному идентификатору
     *
     * @param id уникальный идентификатор маршрута
     */
    public void deleteById(Long id) {
        log.info(DELETE_ROUTE_BY_ID_MESSAGE_LOGGER_REPOSITORY, id);
        dsl.deleteFrom(Routes.ROUTES)
                .where(Routes.ROUTES.ID.eq(id))
                .execute();
    }

    /**
     * Этот метод ищет список маршрутов из базы данных по пункту отправления.
     * Этот метод поддерживает пагинацию с фильрацией количества записей
     *
     * @param departurePoint пункт отправления
     * @param pageable       объект пагинации
     * @return Возвращает список найденных маршрутов
     */
    public List<Route> findRoutesByDeparturePoint(String departurePoint, Pageable pageable) {
        String like = "%" + departurePoint + "%";
        log.info(FIND_ROUTES_BY_DEPARTURE_POINT_MESSAGE_LOGGER_REPOSITORY, departurePoint);
        return dsl.select()
                .from(Routes.ROUTES)
                .where(Routes.ROUTES.DEPARTURE_POINT.likeIgnoreCase(like))
                .orderBy(Routes.ROUTES.ID.asc())
                .offset(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .fetchInto(Route.class);
    }

    /**
     * Этот метод ищет список маршрутов из базы данных по пункту назначения.
     * Этот метод поддерживает пагинацию с фильрацией количества записей
     *
     * @param destination пункт назначения
     * @param pageable    объект пагинации
     * @return Возвращает список найденных маршрутов
     */
    public List<Route> findRoutesByDestination(String destination, Pageable pageable) {
        String like = "%" + destination + "%";
        log.info(FIND_ROUTES_BY_DESTINATION_MESSAGE_LOGGER_REPOSITORY, destination);
        return dsl.select()
                .from(Routes.ROUTES)
                .where(Routes.ROUTES.DESTINATION.likeIgnoreCase(like))
                .orderBy(Routes.ROUTES.ID.asc())
                .offset(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .fetchInto(Route.class);
    }

    /**
     * Этот метод ищет список маршрутов из базы данных по названию компании перевозчика.
     * Этот метод поддерживает пагинацию с фильрацией количества записей
     *
     * @param carrierId уникальный идентификатор названия компании перевозчика
     * @param pageable  объект пагинации
     * @return Возвращает список найденных маршрутов
     */
    public List<Route> findRoutesByCompanyNameCarrier(Long carrierId, Pageable pageable) {
        log.info(FIND_ROUTES_BY_COMPANY_NAME_CARRIER_MESSAGE_LOGGER_REPOSITORY, carrierId);
        return dsl.select()
                .from(Routes.ROUTES)
                .where(Routes.ROUTES.CARRIER_ID.eq(carrierId))
                .orderBy(Routes.ROUTES.ID.asc())
                .offset(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .fetchInto(Route.class);
    }
}
