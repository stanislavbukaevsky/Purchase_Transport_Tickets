package com.github.stanislavbukaevsky.purchasetransporttickets.repository;

import com.github.stanislavbukaevsky.purchasetransporttickets.model.Ticket;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import java.util.Map;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.*;

/**
 * Класс-репозиторий, для связи с хранилищем Redis и вытягиванием из него информации о билетах
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class RedisRepository {
    private static final String KEY = "Ticket";
    @Resource(name = "redisTemplate")
    private HashOperations<String, Long, Ticket> hashOperations;

    /**
     * Этот метод сохраняет информацию о билете в харанилище Redis
     *
     * @param ticket модель билета
     */
    public void save(Ticket ticket) {
        log.info(REDIS_SAVE_MESSAGE_LOGGER_REPOSITORY, ticket);
        hashOperations.putIfAbsent(KEY, ticket.getId(), ticket);
    }

    /**
     * Этот метод удаляет информацию о билете из хранилища Redis
     *
     * @param id уникальный идентификатор билета
     */
    public void delete(Long id) {
        log.info(REDIS_DELETE_MESSAGE_LOGGER_REPOSITORY, id);
        hashOperations.delete(KEY, id);
    }

    /**
     * Этот метод ищет билеты в хранилище Redis
     *
     * @return Возвращает найденные билеты в хранилище
     */
    public Map<Long, Ticket> findAllTickets() {
        log.info(REDIS_FIND_ALL_TICKETS_MESSAGE_LOGGER_REPOSITORY);
        return hashOperations.entries(KEY);
    }
}
