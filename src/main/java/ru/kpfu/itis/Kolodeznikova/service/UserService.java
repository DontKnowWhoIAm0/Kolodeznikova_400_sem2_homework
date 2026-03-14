package ru.kpfu.itis.Kolodeznikova.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.Kolodeznikova.dto.UserDto;
import ru.kpfu.itis.Kolodeznikova.model.Role;
import ru.kpfu.itis.Kolodeznikova.model.User;
import ru.kpfu.itis.Kolodeznikova.repository.RoleRepository;
import ru.kpfu.itis.Kolodeznikova.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(User user) {
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow();
        user.setRoles(List.of(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, String firstName, String secondName) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден. ID: " + id));
        user.setUsername(firstName);
        user.setSecondName(secondName);
        User updated = userRepository.saveAndFlush(user);
        return updated;
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByFirstname(String firstName) {
        return userRepository.findByUsername(firstName);
    }

    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAllUsersDto() {
        return userRepository.findAll().stream()
                .map(u -> new UserDto(u.getUsername(), u.getSecondName())).toList();
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }
}