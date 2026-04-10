package ru.kpfu.itis.Kolodeznikova.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationController {

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }
}
