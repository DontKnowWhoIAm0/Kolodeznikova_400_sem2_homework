package ru.kpfu.itis.Kolodeznikova.entity.enums;

/**
 * Enum representing the preferred ways of communication.
 * Each constant includes a display name in Russian.
 */

public enum WayOfCommunication {
    TELEGRAM("Telegram"),
    WHATSAPP("WhatsApp"),
    VK("Vk"),
    EMAIL("Email"),
    SMS("SMS");

    private final String displayName;

    WayOfCommunication(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
