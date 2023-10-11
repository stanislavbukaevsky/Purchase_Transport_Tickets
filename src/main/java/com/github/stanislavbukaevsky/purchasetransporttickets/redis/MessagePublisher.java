package com.github.stanislavbukaevsky.purchasetransporttickets.redis;

/**
 * Сервис-интерфейс с методами для публикации сообщений в топик Redis в приложении.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface MessagePublisher {
    /**
     * Сигнатура метода для отправки в топик нового сообщения
     *
     * @param message текстовое сообщение
     */
    void publish(String message);
}
