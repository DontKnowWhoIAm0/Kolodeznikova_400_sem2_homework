package ru.kpfu.itis.Kolodeznikova.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.kpfu.itis.Kolodeznikova.dto.ChatMessageDto;
import ru.kpfu.itis.Kolodeznikova.model.ChatMessage;
import ru.kpfu.itis.Kolodeznikova.model.User;
import ru.kpfu.itis.Kolodeznikova.repository.ChatMessageRepository;
import ru.kpfu.itis.Kolodeznikova.service.ChatMessageService;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageServiceImpl(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public ChatMessage save(String content, User author) {
        ChatMessage msg = new ChatMessage(content, LocalDateTime.now(), author);
        return chatMessageRepository.save(msg);
    }

    @Override
    public List<ChatMessageDto> getLast50() {
        return chatMessageRepository.findTop50ByOrderBySentAtDesc()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<ChatMessageDto> getByAuthor(User author) {
        return chatMessageRepository.findByAuthor(author)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id, User currentUser) {
        ChatMessage msg = chatMessageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found"));

        if (!msg.getAuthor().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot delete someone else's message");
        }
        chatMessageRepository.delete(msg);
    }

    @Override
    public void deleteByIdAsAdmin(Long id) {
        ChatMessage msg = chatMessageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found"));
        chatMessageRepository.delete(msg);
    }

    @Override
    public List<ChatMessageDto> getAll() {
        return chatMessageRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public ChatMessageDto toDto(ChatMessage message) {
        return new ChatMessageDto(
                message.getId(),
                message.getContent(),
                message.getAuthor().getUsername(),
                message.getSentAt()
        );
    }
}
