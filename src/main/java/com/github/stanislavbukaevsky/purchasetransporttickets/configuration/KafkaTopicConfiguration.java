package com.github.stanislavbukaevsky.purchasetransporttickets.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.time.Duration;

/**
 * Конфигурационный класс для настройки подписываемого топика
 */
@Configuration
public class KafkaTopicConfiguration {
    @Value("${spring.kafka.topic}")
    private String topic;

    /**
     * Этот метод настраивает и создает новый топик, в который Producer отправляет сообщения
     *
     * @return Возвращает настроенный топик, куда Producer будет отправлять сообщения
     */
    @Bean
    public NewTopic ticketsTopic() {
        return TopicBuilder.name(topic)
                .partitions(3)
                .replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(Duration.ofDays(30).toMillis()))
                .build();
    }
}
