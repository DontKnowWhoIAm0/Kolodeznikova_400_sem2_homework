package ru.kpfu.itis.Kolodeznikova.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.kpfu.itis.Kolodeznikova.config.properties.MailProperties;
import ru.kpfu.itis.Kolodeznikova.dto.CreateUserDto;
import ru.kpfu.itis.Kolodeznikova.exception.EmailAlreadyExistsException;
import ru.kpfu.itis.Kolodeznikova.exception.UsernameAlreadyExistsException;
import ru.kpfu.itis.Kolodeznikova.model.Role;
import ru.kpfu.itis.Kolodeznikova.model.User;
import ru.kpfu.itis.Kolodeznikova.repository.RoleRepository;
import ru.kpfu.itis.Kolodeznikova.repository.UserRepository;

import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @MockitoBean private UserRepository userRepository;
    @MockitoBean private RoleRepository roleRepository;
    @MockitoBean private PasswordEncoder passwordEncoder;
    @MockitoBean private JavaMailSender mailSender;
    @MockitoBean private MailProperties mailProperties;
    @MockitoBean private MimeMessage mimeMessage;

    @BeforeEach
    void setup() {
        given(mailSender.createMimeMessage()).willReturn(mimeMessage);
        given(mailProperties.from()).willReturn("from@example.com");
        given(mailProperties.sender()).willReturn("Sender");
        given(mailProperties.subject()).willReturn("Subject");
        given(mailProperties.content()).willReturn("Hello $name, verify at $url");
        given(mailProperties.baseUrl()).willReturn("http://localhost");
    }

    @Test
    void createUser_existingUsername_throwsException() {
        CreateUserDto dto = new CreateUserDto("user", "pass", "email@example.com");
        given(userRepository.findByUsername(dto.username())).willReturn(Optional.of(new User()));

        assertThrows(UsernameAlreadyExistsException.class, () -> userService.createUser(dto));
    }

    @Test
    void createUser_existingEmail_throwsException() {
        CreateUserDto dto = new CreateUserDto("user", "pass", "email@example.com");
        given(userRepository.findByUsername(dto.username())).willReturn(Optional.empty());
        given(userRepository.findByEmail(dto.email())).willReturn(Optional.of(new User()));

        assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(dto));
    }

    @Test
    void createUser_success_savesUserAndSendsMail() {
        CreateUserDto dto = new CreateUserDto("user", "pass", "email@example.com");
        Role role = new Role();
        role.setName("ROLE_USER");

        given(userRepository.findByUsername(dto.username())).willReturn(Optional.empty());
        given(userRepository.findByEmail(dto.email())).willReturn(Optional.empty());
        given(roleRepository.findByName("ROLE_USER")).willReturn(Optional.of(role));
        given(passwordEncoder.encode(dto.password())).willReturn("encodedPass");

        userService.createUser(dto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User saved = userCaptor.getValue();

        assertEquals("user", saved.getUsername());
        assertEquals("encodedPass", saved.getPassword());
        assertEquals(dto.email(), saved.getEmail());
        assertFalse(saved.isVerified());
        assertNotNull(saved.getVerificationCode());
        assertEquals(1, saved.getRoles().size());
        assertEquals("ROLE_USER", saved.getRoles().get(0).getName());

        verify(mailSender).send(mimeMessage);
    }

    @Test
    void createUser_roleNotFound_throwsException() {
        CreateUserDto dto = new CreateUserDto("user", "pass", "email@example.com");

        given(userRepository.findByUsername(dto.username())).willReturn(Optional.empty());
        given(userRepository.findByEmail(dto.email())).willReturn(Optional.empty());
        given(roleRepository.findByName("ROLE_USER")).willReturn(Optional.empty());
        given(passwordEncoder.encode(dto.password())).willReturn("encodedPass");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.createUser(dto));
        assertEquals("Role USER not found", exception.getMessage());
    }

    @Test
    void createUser_mailSendingFails_throwsRuntimeException() throws Exception {
        CreateUserDto dto = new CreateUserDto("user", "pass", "email@example.com");
        Role role = new Role();
        role.setName("ROLE_USER");

        given(userRepository.findByUsername(dto.username())).willReturn(Optional.empty());
        given(userRepository.findByEmail(dto.email())).willReturn(Optional.empty());
        given(roleRepository.findByName("ROLE_USER")).willReturn(Optional.of(role));
        given(passwordEncoder.encode(dto.password())).willReturn("encodedPass");

        MimeMessage mockMessage = Mockito.mock(MimeMessage.class);
        given(mailSender.createMimeMessage()).willReturn(mockMessage);

        doThrow(new RuntimeException(new MessagingException("fail"))).when(mailSender).send(mockMessage);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.createUser(dto));
        assertTrue(exception.getCause() instanceof MessagingException);
        assertEquals("fail", exception.getCause().getMessage());
    }
}