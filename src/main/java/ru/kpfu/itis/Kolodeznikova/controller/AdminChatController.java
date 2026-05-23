package ru.kpfu.itis.Kolodeznikova.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.Kolodeznikova.dto.ChatMessageDto;
import ru.kpfu.itis.Kolodeznikova.service.ChatMessageService;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminChatController {

    private final ChatMessageService chatMessageService;

    public AdminChatController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @GetMapping("/messages")
    public ResponseEntity<List<ChatMessageDto>> getAllMessages() {
        return ResponseEntity.ok(chatMessageService.getAll());
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        chatMessageService.deleteByIdAsAdmin(id);
        return ResponseEntity.noContent().build();
    }
}

