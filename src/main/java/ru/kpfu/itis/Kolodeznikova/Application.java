package ru.kpfu.itis.Kolodeznikova;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.kpfu.itis.Kolodeznikova.config.properties.MailProperties;

@EnableConfigurationProperties(MailProperties.class)
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
