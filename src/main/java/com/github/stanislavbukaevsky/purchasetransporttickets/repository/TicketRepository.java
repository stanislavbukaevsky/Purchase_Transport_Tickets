package com.github.stanislavbukaevsky.purchasetransporttickets.repository;

import com.github.stanislavbukaevsky.purchasetransporttickets.domain.tables.Tickets;
import com.github.stanislavbukaevsky.purchasetransporttickets.enums.TicketStatus;
import com.github.stanislavbukaevsky.purchasetransporttickets.mapper.record.TicketRecordMapper;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.ExceptionTextMessageConstant.DATA_ACCESS_EXCEPTION_FIND_MESSAGE_REPOSITORY;
import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.ExceptionTextMessageConstant.DATA_ACCESS_EXCEPTION_MESSAGE_REPOSITORY;
import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.*;

/**
 * Класс-репозиторий, для связи с базой данных и вытягивания из нее информации о билете
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class TicketRepository {
    private final DSLContext dsl;
    private final TicketRecordMapper ticketRecordMapper;

    /**
     * Этот метод сохраняет информацию о билете в базу данных
     *
     * @param ticket модель билета
     * @return Возвращает модель билета
     */
    public Ticket save(Ticket ticket) {
        log.info(SAVE_TICKET_MESSAGE_LOGGER_REPOSITORY, ticket);
        return dsl.insertInto(Tickets.TICKETS)
                .set(ticketRecordMapper.unmap(ticket))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException(DATA_ACCESS_EXCEPTION_MESSAGE_REPOSITORY + ticket))
                .into(Ticket.class);
    }

    /**
     * Этот метод изменяет и сохраняет информацию о билете в базе данных
     *
     * @param ticket модель билета
     * @return Возвращает модель билета
     */
    public Ticket update(Ticket ticket) {
        log.info(UPDATE_TICKET_MESSAGE_LOGGER_REPOSITORY, ticket);
        return dsl.update(Tickets.TICKETS)
                .set(ticketRecordMapper.unmap(ticket))
                .where(Tickets.TICKETS.ID.eq(ticket.getId()))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException(DATA_ACCESS_EXCEPTION_MESSAGE_REPOSITORY + ticket))
                .into(Ticket.class);
    }

    /**
     * Этот метод изменяет и сохраняет информацию о билете в базе данных
     *
     * @param ticket модель билета
     * @return Возвращает модель билета
     */
    public Ticket updateStatusTicket(Ticket ticket) {
        log.info(UPDATE_TICKET_MESSAGE_LOGGER_REPOSITORY, ticket);
        return dsl.update(Tickets.TICKETS)
                .set(ticketRecordMapper.unmapBuying(ticket))
                .where(Tickets.TICKETS.ID.eq(ticket.getId()))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException(DATA_ACCESS_EXCEPTION_MESSAGE_REPOSITORY + ticket))
                .into(Ticket.class);
    }

    /**
     * Этот метод ищет билет в базе данных по его уникальному идентификатору
     *
     * @param id уникальный идентификатор билета
     * @return Возвращает модель найденного билета
     */
    public Ticket findTicketById(Long id) {
        log.info(FIND_TICKET_BY_ID_MESSAGE_LOGGER_REPOSITORY, id);
        return dsl.select()
                .from(Tickets.TICKETS)
                .where(Tickets.TICKETS.ID.eq(id))
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException(DATA_ACCESS_EXCEPTION_FIND_MESSAGE_REPOSITORY + id))
                .into(Ticket.class);
    }

    /**
     * Этот метод удаляет билет из базы данных по его уникальному идентификатору
     *
     * @param id уникальный идентификатор билета
     */
    public void deleteById(Long id) {
        log.info(DELETE_TICKET_BY_ID_MESSAGE_LOGGER_REPOSITORY, id);
        dsl.deleteFrom(Tickets.TICKETS)
                .where(Tickets.TICKETS.ID.eq(id))
                .execute();
    }

    /**
     * Этот метод ищет список билетов из базы данных по дате и времени отправления.
     * Этот метод поддерживает пагинацию с фильрацией количества записей
     *
     * @param dateTimeDeparture дата и время отправления
     * @param pageable          объект пагинации
     * @return Возвращает список найденных билетов
     */
    public List<Ticket> findTicketsByDateAndTimeDeparture(LocalDateTime dateTimeDeparture, Pageable pageable) {
        log.info(FIND_TICKETS_BY_DATE_AND_TIME_DEPARTURE_MESSAGE_LOGGER_REPOSITORY, dateTimeDeparture);
        return dsl.select()
                .from(Tickets.TICKETS)
                .where(Tickets.TICKETS.DATE_TIME_DEPARTURE.eq(dateTimeDeparture),
                        Tickets.TICKETS.TICKET_STATUS.eq(TicketStatus.AVAILABLE_FOR_SALE.name()))
                .orderBy(Tickets.TICKETS.DATE_TIME_TICKET_ISSUANCE.asc())
                .offset(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .fetchInto(Ticket.class);
    }

    /**
     * Этот метод ищет список билетов из базы данных по уникальному идентификатору маршрута.
     * Этот метод поддерживает пагинацию с фильрацией количества записей
     *
     * @param routeId  уникальный идентификатор маршрута
     * @param pageable объект пагинации
     * @return Возвращает список найденных билетов
     */
    public List<Ticket> findTicketsByRouteId(Long routeId, Pageable pageable) {
        log.info(FIND_TICKETS_BY_ROUTE_ID_MESSAGE_LOGGER_REPOSITORY, routeId);
        return dsl.select()
                .from(Tickets.TICKETS)
                .where(Tickets.TICKETS.ROUTE_ID.eq(routeId),
                        Tickets.TICKETS.TICKET_STATUS.eq(TicketStatus.AVAILABLE_FOR_SALE.name()))
                .orderBy(Tickets.TICKETS.ID.asc())
                .offset(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .fetchInto(Ticket.class);
    }

    /**
     * Этот метод ищет список билетов из базы данных по уникальному идентификатору пользователя.
     * Этот метод поддерживает пагинацию с фильрацией количества записей
     *
     * @param userId   уникальный идентификатор пользователя
     * @param pageable объект пагинации
     * @return Возвращает список найденных билетов
     */
    public List<Ticket> findTicketsByUserId(Long userId, Pageable pageable) {
        log.info(FIND_TICKETS_BY_USER_ID_MESSAGE_LOGGER_REPOSITORY, userId);
        return dsl.select()
                .from(Tickets.TICKETS)
                .where(Tickets.TICKETS.USER_ID.eq(userId))
                .orderBy(Tickets.TICKETS.ID.asc())
                .offset(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .fetchInto(Ticket.class);
    }
}

