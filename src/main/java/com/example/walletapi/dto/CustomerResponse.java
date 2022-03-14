package com.example.walletapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponse {
    private String message;
    private String fullName;
    private String UserName;
    private String email;
    private String acc;
    private Double balance;
}
