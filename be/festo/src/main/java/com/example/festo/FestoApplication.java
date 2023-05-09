package com.example.festo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FestoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FestoApplication.class, args);
    }

}
