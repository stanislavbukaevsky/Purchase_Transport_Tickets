package com.github.stanislavbukaevsky.purchasetransporttickets.enums;

/**
 * Перечисление, для разделения ролей всех пользователей
 */
public enum Role {
    BUYER("Покупатель"), ADMINISTRATOR("Администратор");
    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
