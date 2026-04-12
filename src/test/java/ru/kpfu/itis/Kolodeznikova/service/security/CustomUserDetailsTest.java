package ru.kpfu.itis.Kolodeznikova.service.security;

import org.junit.jupiter.api.Test;
import ru.kpfu.itis.Kolodeznikova.model.Role;
import ru.kpfu.itis.Kolodeznikova.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsTest {

    @Test
    void getAuthorities_returnsRolesAsGrantedAuthorities() {
        Role role = new Role();
        role.setName("ROLE_USER");
        User user = new User();
        user.setRoles(List.of(role));

        CustomUserDetails userDetails = new CustomUserDetails(user);

        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void getPasswordAndUsername_returnsCorrectValues() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("secret");

        CustomUserDetails userDetails = new CustomUserDetails(user);

        assertEquals("testUser", userDetails.getUsername());
        assertEquals("secret", userDetails.getPassword());
    }

    @Test
    void isEnabled_returnsUserVerifiedStatus() {
        User user = new User();
        user.setVerified(true);

        CustomUserDetails userDetails = new CustomUserDetails(user);

        assertTrue(userDetails.isEnabled());
    }

    @Test
    void getUser_returnsWrappedUser() {
        User user = new User();
        CustomUserDetails userDetails = new CustomUserDetails(user);

        assertEquals(user, userDetails.getUser());
    }
}