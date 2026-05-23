package ru.kpfu.itis.Kolodeznikova.service;

import ru.kpfu.itis.Kolodeznikova.dto.ChatMessageDto;
import ru.kpfu.itis.Kolodeznikova.model.ChatMessage;
import ru.kpfu.itis.Kolodeznikova.model.User;

import java.util.List;

public interface ChatMessageService {
    ChatMessage save(String content, User author);
    List<ChatMessageDto> getLast50();
    List<ChatMessageDto> getByAuthor(User author);
    void deleteById(Long id, User currentUser);
    void deleteByIdAsAdmin(Long id);
    List<ChatMessageDto> getAll();
    ChatMessageDto toDto(ChatMessage message);
}