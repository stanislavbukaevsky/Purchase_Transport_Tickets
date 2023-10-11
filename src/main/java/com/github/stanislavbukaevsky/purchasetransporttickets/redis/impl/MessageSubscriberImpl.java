package com.github.stanislavbukaevsky.purchasetransporttickets.redis.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.ON_MESSAGE_LISTENER_MESSAGE_LOGGER_SERVICE;

/**
 * Сервис-класс с бизнес-логикой для получения сообщений из топика Redis.
 * Реализует интерфейс {@link MessageListener}
 */
@Slf4j
@Service
public class MessageSubscriberImpl implements MessageListener {
    public static List<String> messages = new ArrayList<String>();

    /**
     * Реализация метода для получения нового сообщения из топика
     *
     * @param message объект сообщения
     * @param pattern массив байт
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info(ON_MESSAGE_LISTENER_MESSAGE_LOGGER_SERVICE + new String(message.getBody()));
        messages.add(message.toString());
    }
}
