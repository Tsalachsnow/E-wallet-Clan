package com.example.walletapi.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TransferDto {

    @Pattern(regexp = "^(07)\\d{8}\\b")
    private String acc;
    private Double amount;
    private String email;


}
