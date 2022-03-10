package com.example.walletapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {
    @GetMapping("/")
    public String createWallet(){
        return ("<h1>welcome</h1>");
    }
}
