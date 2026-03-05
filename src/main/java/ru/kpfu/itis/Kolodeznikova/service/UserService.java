package ru.kpfu.itis.Kolodeznikova.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.Kolodeznikova.dto.UserDto;
import ru.kpfu.itis.Kolodeznikova.model.User;
import ru.kpfu.itis.Kolodeznikova.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    @Transactional(readOnly = true)
    public User getUserById(Long id) { return userRepository.findById(id); }

    @Transactional
    public void createUser(User user) { userRepository.save(user); }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsersDto() {
        return userRepository.findAll().stream()
                .map(u -> new UserDto(u.getFirstName(), u.getSecondName())).toList();
    }
}