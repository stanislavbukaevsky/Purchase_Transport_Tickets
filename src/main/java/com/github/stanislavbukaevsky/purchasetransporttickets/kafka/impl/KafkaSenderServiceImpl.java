package com.github.stanislavbukaevsky.purchasetransporttickets.kafka.impl;

import com.github.stanislavbukaevsky.purchasetransporttickets.dto.BuyingTicketResponseDto;
import com.github.stanislavbukaevsky.purchasetransporttickets.kafka.KafkaSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.KAFKA_SEND_MESSAGE_LOGGER_SERVICE;

/**
 * Сервис-класс с бизнес-логикой для Kafka в приложении.
 * Реализует интерфейс {@link KafkaSenderService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaSenderServiceImpl implements KafkaSenderService {
    private final KafkaSender<String, Object> kafkaSender;
    @Value("${spring.kafka.topic}")
    private String topic;

    /**
     * Реализация метода для отправки сообщений в топик Kafka
     *
     * @param buyingTicketResponseDto объект DTO с запросом от пользователя
     */
    public void send(BuyingTicketResponseDto buyingTicketResponseDto) {
        log.info(KAFKA_SEND_MESSAGE_LOGGER_SERVICE, buyingTicketResponseDto);
        kafkaSender.send(Mono.just(
                SenderRecord.create(
                        topic,
                        0,
                        System.currentTimeMillis(),
                        String.valueOf(buyingTicketResponseDto.hashCode()),
                        buyingTicketResponseDto,
                        null
                )
        )).subscribe();
    }
}
