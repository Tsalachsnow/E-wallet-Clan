package com.example.walletapi.dto;

import com.example.walletapi.models.TransactionType;
import com.example.walletapi.models.Wallet;
import java.time.LocalDateTime;
import lombok.Data;


@Data
public class TransactionDto {
    private Wallet wallet;
    private TransactionType transactionType;
    private Double amount;
    private LocalDateTime time = LocalDateTime.now();

}
