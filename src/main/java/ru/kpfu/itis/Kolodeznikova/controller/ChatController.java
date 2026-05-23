package ru.kpfu.itis.Kolodeznikova.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.Kolodeznikova.model.User;
import ru.kpfu.itis.Kolodeznikova.service.ChatMessageService;
import ru.kpfu.itis.Kolodeznikova.service.security.CustomUserDetails;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private final ChatMessageService chatMessageService;

    public ChatController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @GetMapping
    public String getChat(Model model) {
        model.addAttribute("messages", chatMessageService.getLast50());
        model.addAttribute("currentUsername", getUser().getUsername());
        return "chat";
    }

    @GetMapping("/public")
    public String getPublicChat(Model model) {
        model.addAttribute("messages", chatMessageService.getLast50());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String)) {
            model.addAttribute("currentUsername", auth.getName());
        }

        return "public_chat";
    }

    @GetMapping("/my")
    public String getMyMessages(Model model) {
        model.addAttribute("messages", chatMessageService.getByAuthor(getUser()));
        return "my_messages";
    }

    @PostMapping("/{id}/delete")
    public String deleteMessage(@PathVariable Long id) {
        chatMessageService.deleteById(id, getUser());
        return "redirect:/chat/my";
    }

    private User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUser();
        }
        return (User) principal;
    }
}
