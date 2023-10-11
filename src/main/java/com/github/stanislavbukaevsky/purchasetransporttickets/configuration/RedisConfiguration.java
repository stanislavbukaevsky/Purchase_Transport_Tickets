package com.github.stanislavbukaevsky.purchasetransporttickets.configuration;

import com.github.stanislavbukaevsky.purchasetransporttickets.redis.MessagePublisher;
import com.github.stanislavbukaevsky.purchasetransporttickets.redis.impl.MessagePublisherImpl;
import com.github.stanislavbukaevsky.purchasetransporttickets.redis.impl.MessageSubscriberImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * Конфигурационный класс для настройки конфигурации Redis
 */
@Configuration
public class RedisConfiguration {
    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private Integer port;
    @Value("${spring.data.redis.topic}")
    private String topic;

    /**
     * Этот метод создает и настраивает соединение с Redis
     *
     * @return Возвращает настроенное соединение с Redis
     */
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        final RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
        return new LettuceConnectionFactory(configuration);
    }

    /**
     * Этот метод создает и настраивает взаимодействие с сервером Redis
     *
     * @return Возвращает настроенное взаимодействие с сервером Redis
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory());
        return template;
    }

    /**
     * Этот метод создает и настраивает канал связи с Redis
     *
     * @return Возвращает настроенный канал связи с Redis
     */
    @Bean
    public ChannelTopic channelTopic() {
        return new ChannelTopic(topic);
    }

    /**
     * Этот метод создает и настраивает связь для публикации сообщений
     *
     * @return Возвращает настроенную связь для публикации сообщений
     */
    @Bean
    public MessagePublisher redisPublisher() {
        return new MessagePublisherImpl(redisTemplate(), channelTopic());
    }

    /**
     * Этот метод создает и настраивает адаптер для прослущивания сообщений
     *
     * @return Возвращает настроенный адаптер для прослущивания сообщений
     */
    @Bean
    public MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(new MessageSubscriberImpl());
    }
}
