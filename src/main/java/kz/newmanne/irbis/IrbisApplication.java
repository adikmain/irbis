package kz.newmanne.irbis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class IrbisApplication {
    public static void main(String[] args) {
        SpringApplication.run(IrbisApplication.class, args);
    }
}
