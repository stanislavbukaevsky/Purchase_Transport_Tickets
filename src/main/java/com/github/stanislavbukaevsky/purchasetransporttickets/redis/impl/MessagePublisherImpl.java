package com.github.stanislavbukaevsky.purchasetransporttickets.redis.impl;

import com.github.stanislavbukaevsky.purchasetransporttickets.redis.MessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.PUBLISH_MESSAGE_LOGGER_SERVICE;

/**
 * Сервис-класс с бизнес-логикой для публикации сообщений в топик Redis.
 * Реализует интерфейс {@link MessagePublisher}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessagePublisherImpl implements MessagePublisher {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic channelTopic;

    /**
     * Реализация метода для отправки в топик нового сообщения
     *
     * @param message текстовое сообщение
     */
    @Override
    public void publish(String message) {
        log.info(PUBLISH_MESSAGE_LOGGER_SERVICE, message);
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
    }
}
