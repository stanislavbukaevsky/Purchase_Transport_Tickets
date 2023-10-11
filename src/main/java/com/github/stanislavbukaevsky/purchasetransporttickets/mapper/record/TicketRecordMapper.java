package com.github.stanislavbukaevsky.purchasetransporttickets.mapper.record;

import com.github.stanislavbukaevsky.purchasetransporttickets.domain.tables.Tickets;
import com.github.stanislavbukaevsky.purchasetransporttickets.domain.tables.records.TicketsRecord;
import com.github.stanislavbukaevsky.purchasetransporttickets.enums.TicketStatus;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;
import org.springframework.stereotype.Component;

/**
 * Класс-маппер, который преобразует информацию о билете в сущность. <br>
 * Этот класс расширяет интерфейс {@link RecordUnmapper}. Параметры: <br>
 * {@link Ticket} - модель билета <br>
 * {@link TicketsRecord} - сущность билета
 */
@Component
@RequiredArgsConstructor
public class TicketRecordMapper implements RecordUnmapper<Ticket, TicketsRecord> {
    private final DSLContext dsl;

    /**
     * Этот метод преобразует модель билета в сущность
     *
     * @param ticket модель билета
     * @return Возвращает сформированную сущность билета
     * @throws MappingException исключение, возникающее при сбое запросов связанной службы сопоставления
     */
    @Override
    public @NotNull TicketsRecord unmap(Ticket ticket) throws MappingException {
        TicketsRecord ticketsRecord = dsl.newRecord(Tickets.TICKETS, ticket);
        ticketsRecord.setRouteId(ticket.getRoute().getId());
        ticketsRecord.setDateTimeDeparture(ticket.getDateTimeDeparture());
        ticketsRecord.setSeatNumber(ticket.getSeatNumber());
        ticketsRecord.setPrice(ticket.getPrice());
        ticketsRecord.setDateTimeTicketIssuance(ticket.getDateTimeTicketIssuance());
        ticketsRecord.setTicketStatus(TicketStatus.AVAILABLE_FOR_SALE.name());
        return ticketsRecord;
    }

    /**
     * Этот метод преобразует модель билета в сущность с дополнительными полями.
     * Служит для покупки билета пользователем
     *
     * @param ticket модель билета
     * @return Возвращает сформированную сущность билета
     */
    public @NotNull TicketsRecord unmapBuying(Ticket ticket) {
        TicketsRecord ticketsRecord = dsl.newRecord(Tickets.TICKETS, ticket);
        ticketsRecord.setRouteId(ticket.getRouteId());
        ticketsRecord.setDateTimeDeparture(ticket.getDateTimeDeparture());
        ticketsRecord.setSeatNumber(ticket.getSeatNumber());
        ticketsRecord.setPrice(ticket.getPrice());
        ticketsRecord.setDateTimeTicketIssuance(ticket.getDateTimeTicketIssuance());
        ticketsRecord.setUserId(ticket.getUserId());
        ticketsRecord.setTicketStatus(TicketStatus.NOT_ON_SALE.name());
        return ticketsRecord;
    }
}
