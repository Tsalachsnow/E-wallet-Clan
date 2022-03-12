package com.example.walletapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WalletApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletApiApplication.class, args);
    }

}
