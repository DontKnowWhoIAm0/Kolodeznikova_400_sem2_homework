package ru.kpfu.itis.Kolodeznikova.controller.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.kpfu.itis.Kolodeznikova.model.User;
import ru.kpfu.itis.Kolodeznikova.repository.UserRepository;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(VerificationController.class)
class VerificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    @WithMockUser
    void testVerifyUserSuccess() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setVerificationCode("123");

        given(userRepository.findAll()).willReturn(List.of(user));

        mockMvc.perform(get("/verification").param("code", "123"))
                .andExpect(status().isOk())
                .andExpect(view().name("verification_success"));

        verify(userRepository).save(user);
    }

    @Test
    @WithMockUser
    void testVerifyUserFailed() throws Exception {
        given(userRepository.findAll()).willReturn(List.of());

        mockMvc.perform(get("/verification").param("code", "wrong"))
                .andExpect(status().isOk())
                .andExpect(view().name("verification_failed"));
    }
}
