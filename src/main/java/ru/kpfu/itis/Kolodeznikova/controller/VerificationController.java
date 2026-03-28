package ru.kpfu.itis.Kolodeznikova.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.Kolodeznikova.model.User;
import ru.kpfu.itis.Kolodeznikova.repository.UserRepository;

import java.util.Optional;

@Controller
public class VerificationController {

    private final UserRepository userRepository;

    public VerificationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/verification")
    public String verifyUser(@RequestParam("code") String code) {
        Optional<User> optionalUser = userRepository.findAll()
                .stream()
                .filter(user -> code.equals(user.getVerificationCode()))
                .findFirst();

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setVerified(true);
            user.setVerificationCode(null);
            userRepository.save(user);
            return "verification_success";
        } else {
            return "verification_failed";
        }
    }
}
