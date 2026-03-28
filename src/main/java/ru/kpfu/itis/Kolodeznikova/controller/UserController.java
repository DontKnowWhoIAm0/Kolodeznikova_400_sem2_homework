package ru.kpfu.itis.Kolodeznikova.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.Kolodeznikova.dto.CreateUserDto;
import ru.kpfu.itis.Kolodeznikova.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/users")
    public String createUser(@ModelAttribute CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return "check_verification_code";
    }
}
