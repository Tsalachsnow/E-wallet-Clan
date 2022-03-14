package com.example.walletapi.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationRequest {
    private String username;
    private String password;
}
