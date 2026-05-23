package ru.kpfu.itis.Kolodeznikova.dto;

import java.time.LocalDateTime;

public class ChatMessageDto {

    private Long id;
    private String content;
    private String authorUsername;
    private LocalDateTime sentAt;

    public ChatMessageDto() {}

    public ChatMessageDto(Long id, String content, String authorUsername, LocalDateTime sentAt) {
        this.id = id;
        this.content = content;
        this.authorUsername = authorUsername;
        this.sentAt = sentAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getAuthorUsername() { return authorUsername; }
    public void setAuthorUsername(String authorUsername) { this.authorUsername = authorUsername; }

    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
}
