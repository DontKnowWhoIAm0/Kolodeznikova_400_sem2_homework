package ru.kpfu.itis.Kolodeznikova.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.Kolodeznikova.config.properties.MailProperties;
import ru.kpfu.itis.Kolodeznikova.dto.CreateUserDto;
import ru.kpfu.itis.Kolodeznikova.exception.EmailAlreadyExistsException;
import ru.kpfu.itis.Kolodeznikova.exception.UsernameAlreadyExistsException;
import ru.kpfu.itis.Kolodeznikova.model.User;
import ru.kpfu.itis.Kolodeznikova.model.Role;
import ru.kpfu.itis.Kolodeznikova.repository.RoleRepository;
import ru.kpfu.itis.Kolodeznikova.repository.UserRepository;
import ru.kpfu.itis.Kolodeznikova.service.UserService;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailProperties mailProperties;
    private final JavaMailSender mailSender;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, RoleRepository roleRepository1, PasswordEncoder passwordEncoder,
                           JavaMailSender mailSender, MailProperties mailProperties) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository1;
        this.passwordEncoder = passwordEncoder;
        this.mailProperties = mailProperties;
        this.mailSender = mailSender;
    }

    @Override
    public void createUser(CreateUserDto createUserDto) {
        if (userRepository.findByUsername(createUserDto.username()).isPresent()) {
            throw new UsernameAlreadyExistsException("Такое имя пользователя " + createUserDto.username() + " уже занят");
        }
        if (userRepository.findByEmail(createUserDto.email()).isPresent()) {
            throw new EmailAlreadyExistsException("Email " + createUserDto.email() + " уже занят");
        }

        User user = new User();
        user.setUsername(createUserDto.username());
        user.setPassword(passwordEncoder.encode(createUserDto.password()));
        user.setEmail(createUserDto.email());
        user.setVerified(false);
        String verificationCode = UUID.randomUUID().toString();
        user.setVerificationCode(verificationCode);

        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role USER not found"));
        user.setRoles(List.of(userRole));

        userRepository.save(user);
        sendVerificationMail(createUserDto, verificationCode);
    }

    private void sendVerificationMail(CreateUserDto createUserDto, String verificationCode) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        String content = mailProperties.content();
        try {
            mimeMessageHelper.setFrom(mailProperties.from(), mailProperties.sender());
            mimeMessageHelper.setTo(createUserDto.email());
            mimeMessageHelper.setSubject(mailProperties.subject());

            content = content.replace("$name", createUserDto.username());
            content = content.replace("$url", mailProperties.baseUrl() +
                    "/verification?code=" + verificationCode);

            mimeMessageHelper.setText(content, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
