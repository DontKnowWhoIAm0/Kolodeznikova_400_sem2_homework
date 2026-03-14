package ru.kpfu.itis.Kolodeznikova.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.Kolodeznikova.dto.UserDto;
import ru.kpfu.itis.Kolodeznikova.model.User;
import ru.kpfu.itis.Kolodeznikova.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create")
    public User createUser(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "secondName") String secondName) {
        User user = new User();
        user.setUsername(firstName);
        user.setSecondName(secondName);
        return userService.createUser(user);
    }

    @GetMapping("/get-by-id")
    public User getUserById(@RequestParam(name = "id") Long id) {
        Optional<User> userOpt = userService.getUserById(id);
        return userOpt.orElse(null);
    }

    @GetMapping("/get-by-firstname")
    public User getUserByFirstname(@RequestParam(name = "firstName") String firstName) {
        Optional<User> userOpt = userService.getUserByFirstname(firstName);
        return userOpt.orElse(null);
    }

    @GetMapping("/update")
    public User updateUser(@RequestParam(name = "id") Long id,
                             @RequestParam(name = "firstName") String firstName,
                             @RequestParam(name = "secondName") String secondName) {
        return userService.updateUser(id, firstName, secondName);
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam(name = "id") Long id) {
        userService.deleteUserById(id);
        return "Удален";
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> findAll() { return userService.findAllUsersDto(); }
}
