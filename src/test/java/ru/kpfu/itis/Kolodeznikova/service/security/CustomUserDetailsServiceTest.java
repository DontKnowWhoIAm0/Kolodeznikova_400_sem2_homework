package ru.kpfu.itis.Kolodeznikova.service.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.kpfu.itis.Kolodeznikova.model.User;
import ru.kpfu.itis.Kolodeznikova.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class CustomUserDetailsServiceTest {

    @MockitoBean
    private UserRepository userRepository;

    @Test
    void loadUserByUsername_existingUser_returnsCustomUserDetails() {
        User user = new User();
        user.setUsername("testUser");

        given(userRepository.findByUsername("testUser")).willReturn(Optional.of(user));

        CustomUserDetailsService service = new CustomUserDetailsService(userRepository);
        UserDetails userDetails = service.loadUserByUsername("testUser");

        assertTrue(userDetails instanceof CustomUserDetails);
        assertEquals("testUser", userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_nonExistingUser_throwsException() {
        given(userRepository.findByUsername("unknown")).willReturn(Optional.empty());

        CustomUserDetailsService service = new CustomUserDetailsService(userRepository);

        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("unknown"));
    }
}
