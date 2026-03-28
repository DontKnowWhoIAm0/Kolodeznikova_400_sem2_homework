package ru.kpfu.itis.Kolodeznikova.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public String handleUsernameAlreadyExists(UsernameAlreadyExistsException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "register";
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public String handleEmailAlreadyExists(EmailAlreadyExistsException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "register";
    }
}
