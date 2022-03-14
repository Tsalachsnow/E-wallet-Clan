package com.example.walletapi.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
public class AuthenticationResponse {
    private String jwt;
    private String role;
}
