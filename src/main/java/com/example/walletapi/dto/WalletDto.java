package com.example.walletapi.dto;

import com.example.walletapi.models.Customer;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class WalletDto extends BaseClassDto{
    private String name;
    private String currency;
    private Double balance;
    private String acc;
    private LocalDateTime created;
}
