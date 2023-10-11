package com.github.stanislavbukaevsky.purchasetransporttickets.enums;

/**
 * Перечисление, для разделения статусов транспортных билетов
 */
public enum TicketStatus {
    NOT_ON_SALE("Нет в продаже"), AVAILABLE_FOR_SALE("Есть в продаже");

    private final String description;

    TicketStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
