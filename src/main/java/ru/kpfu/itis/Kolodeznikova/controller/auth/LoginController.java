package ru.kpfu.itis.Kolodeznikova.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kpfu.itis.Kolodeznikova.aop.execution_time.ExecutionTime;

@Controller
public class LoginController {

    @ExecutionTime
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
}
