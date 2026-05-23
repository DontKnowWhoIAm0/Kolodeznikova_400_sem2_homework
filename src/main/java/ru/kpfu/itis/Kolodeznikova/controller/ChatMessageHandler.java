package ru.kpfu.itis.Kolodeznikova.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import ru.kpfu.itis.Kolodeznikova.dto.ChatMessageDto;
import ru.kpfu.itis.Kolodeznikova.model.ChatMessage;
import ru.kpfu.itis.Kolodeznikova.model.User;
import ru.kpfu.itis.Kolodeznikova.service.ChatMessageService;
import ru.kpfu.itis.Kolodeznikova.service.security.CustomUserDetails;

import java.security.Principal;

@Controller
public class ChatMessageHandler {

    private final ChatMessageService chatMessageService;

    public ChatMessageHandler(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public ChatMessageDto handleMessage(ChatMessageDto incoming, SimpMessageHeaderAccessor headerAccessor) {
        Principal principal = headerAccessor.getUser();
        User author = extractUser(principal);

        ChatMessage saved = chatMessageService.save(incoming.getContent(), author);
        return chatMessageService.toDto(saved);
    }

    private User extractUser(Principal principal) {
        if (principal instanceof org.springframework.security.authentication.UsernamePasswordAuthenticationToken token) {
            Object details = token.getPrincipal();
            if (details instanceof CustomUserDetails ud) {
                return ud.getUser();
            }
        }
        throw new IllegalStateException("Cannot resolve user from WebSocket session");
    }
}
