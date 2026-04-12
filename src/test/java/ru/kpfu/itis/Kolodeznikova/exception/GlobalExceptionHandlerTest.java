package ru.kpfu.itis.Kolodeznikova.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import({GlobalExceptionHandler.class, GlobalExceptionHandlerTest.TestController.class})
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @RestController
    static class TestController {

        @GetMapping("/username-exists")
        public void throwUsernameException() {
            throw new UsernameAlreadyExistsException("Ник уже занят");
        }

        @GetMapping("/email-exists")
        public void throwEmailException() {
            throw new EmailAlreadyExistsException("Такая почта уже занята");
        }
    }

    @Test
    @WithMockUser
    void usernameAlreadyExistsException_isHandledCorrectly() throws Exception {
        mockMvc.perform(get("/username-exists"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attribute("errorMessage", "Ник уже занят"));
    }

    @Test
    @WithMockUser
    void emailAlreadyExistsException_isHandledCorrectly() throws Exception {
        mockMvc.perform(get("/email-exists"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attribute("errorMessage", "Такая почта уже занята"));
    }

    @Test
    void usernameAlreadyExistsException_messageTest() {
        UsernameAlreadyExistsException ex = new UsernameAlreadyExistsException("Ник уже занят");
        assertThat(ex.getMessage()).isEqualTo("Ник уже занят");
    }

    @Test
    void emailAlreadyExistsException_messageTest() {
        EmailAlreadyExistsException ex = new EmailAlreadyExistsException("Такая почта уже занята");
        assertThat(ex.getMessage()).isEqualTo("Такая почта уже занята");
    }
}