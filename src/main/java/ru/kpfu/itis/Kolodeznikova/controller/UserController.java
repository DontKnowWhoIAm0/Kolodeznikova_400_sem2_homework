package ru.kpfu.itis.Kolodeznikova.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.Kolodeznikova.dto.UserDto;
import ru.kpfu.itis.Kolodeznikova.service.UserService;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> findAll() { return userService.getAllUsersDto(); }

}
