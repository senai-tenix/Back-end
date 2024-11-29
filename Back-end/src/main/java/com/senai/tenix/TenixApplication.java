package com.senai.tenix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:8081")  // Permite o acesso do frontend no endereço especificado
public class TenixApplication {
    public static void main(String[] args) {
        SpringApplication.run(TenixApplication.class, args);
    }
}
