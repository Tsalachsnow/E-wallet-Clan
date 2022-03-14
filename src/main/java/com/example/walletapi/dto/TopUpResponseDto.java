package com.example.walletapi.dto;

import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class TopUpResponseDto {
    @Min(value = 50)
    private Double amount;
//    private String acc;
    private String transactionType;
    private String walletId;
}
