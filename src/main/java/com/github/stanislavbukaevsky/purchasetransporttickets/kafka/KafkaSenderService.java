package com.github.stanislavbukaevsky.purchasetransporttickets.kafka;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.BuyingTicketResponseDto;

/**
 * Сервис-интерфейс с методами для Kafka в приложении.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface KafkaSenderService {
    /**
     * Сигнатура метода для отправки сообщений в топик Kafka
     *
     * @param buyingTicketResponseDto объект DTO с запросом от пользователя
     */
    void send(BuyingTicketResponseDto buyingTicketResponseDto);
}
