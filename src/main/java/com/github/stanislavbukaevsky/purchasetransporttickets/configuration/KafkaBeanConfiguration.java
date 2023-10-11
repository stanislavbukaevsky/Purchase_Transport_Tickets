package com.github.stanislavbukaevsky.purchasetransporttickets.configuration;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Конфигурационный класс для настройки сериализации Producer
 */
@Configuration
public class KafkaBeanConfiguration {
    private static final String PATH_FILE = "src/main/resources/kafka/producer.xml";

    /**
     * Этот метод настраивает сериализацию Producer, которую парсит из файла xml
     *
     * @return Возвращает преобразованный xml файл
     */
    @Bean
    @SneakyThrows
    public XML producerXML() {
        return new XMLDocument(new File(PATH_FILE));
    }
}
