package com.github.stanislavbukaevsky.purchasetransporttickets.mapper;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.BuyingTicketResponseDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.TicketRequestDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.dto.TicketResponseDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.enums.TicketStatus;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер-интерфейс, который преобразует информацию о билете в DTO
 */
@Mapper
public interface TicketMapper {
    /**
     * Этот метод преобразует полученные поля из DTO в модель, для получения информации о билете. <br>
     * Используется аннотация {@link Mapping} для игнорирования маппинга полей
     *
     * @param ticketRequestDto DTO запроса с информацией о билете
     * @return Возвращает сформированную модель с информацией о билете
     */
    @Mapping(ignore = true, target = "dateTimeDeparture")
    Ticket toTicketModel(TicketRequestDto ticketRequestDto);

    /**
     * Этот метод преобразует полученные поля из модели в DTO, для получения информации о билете. <br>
     * Используется аннотация {@link Mapping} для соответствия полей
     *
     * @param ticket модель с информацией о билете
     * @return Возвращает сформированную DTO с ответом о билете
     */
    @Mapping(source = "ticket.ticketStatus", target = "ticketStatus")
    TicketResponseDto toTicketResponseDto(Ticket ticket);

    /**
     * Этот метод преобразует полученные поля из модели в DTO, для получения информации о купленном билете. <br>
     * Используется аннотация {@link Mapping} для соответствия полей
     *
     * @param ticket модель с информацией о билете
     * @return Возвращает сформированную DTO с ответом о купленном билете
     */
    @Mapping(source = "ticket.ticketStatus", target = "ticketStatus")
    BuyingTicketResponseDto toBuyingTicketResponseDto(Ticket ticket);

    /**
     * Этот метод формирует описание перечисления
     *
     * @param ticketStatus перечисление о статусе билета
     * @return Возвращает подробное описание определенного перечисления
     */
    default String toTicketStatusDescription(TicketStatus ticketStatus) {
        return ticketStatus.getDescription();
    }
}
