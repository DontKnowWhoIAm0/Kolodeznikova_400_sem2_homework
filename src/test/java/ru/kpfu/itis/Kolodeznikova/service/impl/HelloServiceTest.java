package ru.kpfu.itis.Kolodeznikova.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HelloServiceTest {

    @Autowired
    private HelloService helloService;

    @Test
    void sayHello_returnsGreetingWithName() {
        String result = helloService.sayHello("Sonya");
        assertEquals("Hello, Sonya", result);
    }

    @Test
    void sayHello_returnsGreetingWithEmptyString() {
        String result = helloService.sayHello("");
        assertEquals("Hello, ", result);
    }

    @Test
    void sayHello_returnsGreetingWithNull() {
        String result = helloService.sayHello(null);
        assertEquals("Hello, null", result);
    }
}
