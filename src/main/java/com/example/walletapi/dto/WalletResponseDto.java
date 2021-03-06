package com.example.walletapi.dto;

import com.example.walletapi.models.Wallet;

public class WalletResponseDto {

    public WalletDto toModel(Wallet entity) {
        WalletDto dto = new WalletDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setBalance(entity.getBalance());
        dto.setCurrency(entity.getCurrency().toString());
        dto.setCreated(entity.getCreated());
        return dto;
    }

}
