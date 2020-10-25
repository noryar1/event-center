package org.noryar.eventcenter.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.noryar")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
