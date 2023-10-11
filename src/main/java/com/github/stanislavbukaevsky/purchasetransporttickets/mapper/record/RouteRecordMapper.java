package com.github.stanislavbukaevsky.purchasetransporttickets.mapper.record;

import com.github.stanislavbukaevsky.purchasetransporttickets.domain.tables.Routes;
import com.github.stanislavbukaevsky.purchasetransporttickets.domain.tables.records.RoutesRecord;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.Route;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;
import org.springframework.stereotype.Component;

/**
 * Класс-маппер, который преобразует информацию о маршруте в сущность. <br>
 * Этот класс расширяет интерфейс {@link RecordUnmapper}. Параметры: <br>
 * {@link Route} - модель маршрута <br>
 * {@link RoutesRecord} - сущность маршрута
 */
@Component
@RequiredArgsConstructor
public class RouteRecordMapper implements RecordUnmapper<Route, RoutesRecord> {
    private final DSLContext dsl;

    /**
     * Этот метод преобразует модель маршрута в сущность
     *
     * @param route модель маршрута
     * @return Возвращает сформированную сущность маршрута
     * @throws MappingException исключение, возникающее при сбое запросов связанной службы сопоставления
     */
    @Override
    public @NotNull RoutesRecord unmap(Route route) throws MappingException {
        RoutesRecord routesRecord = dsl.newRecord(Routes.ROUTES, route);
        routesRecord.setDeparturePoint(route.getDeparturePoint());
        routesRecord.setDestination(route.getDestination());
        routesRecord.setCarrierId(route.getCarrier().getId());
        routesRecord.setDurationInMinutes(route.getDurationInMinutes());
        return routesRecord;
    }
}
