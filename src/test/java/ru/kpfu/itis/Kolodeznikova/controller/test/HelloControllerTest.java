package ru.kpfu.itis.Kolodeznikova.controller.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.kpfu.itis.Kolodeznikova.repository.UserRepository;
import ru.kpfu.itis.Kolodeznikova.service.impl.HelloService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HelloService helloService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    @WithMockUser
    void testHelloWithName() throws Exception {
        given(helloService.sayHello("Sonya")).willReturn("Hello, Sonya!");

        mockMvc.perform(get("/hello").param("name", "Sonya"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, Sonya!"));
    }

    @Test
    @WithMockUser
    void testGetUsers() throws Exception {
        var user = new ru.kpfu.itis.Kolodeznikova.model.User();
        user.setId(1L);
        user.setUsername("Sonya");
        given(userRepository.findAll()).willReturn(List.of(user));

        mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username").value("Sonya"));
    }
}
