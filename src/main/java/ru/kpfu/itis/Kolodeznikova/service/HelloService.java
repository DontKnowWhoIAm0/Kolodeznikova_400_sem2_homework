package ru.kpfu.itis.Kolodeznikova.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String sayHello(String name) {
        return "Hello, %s".formatted(name);
    }
}
