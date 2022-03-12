package com.example.walletapi.dto;

import com.example.walletapi.models.Wallet;
import java.util.ArrayList;
import java.util.List;

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

    public List<WalletDto> toModel(List<Wallet> entities) {
        if (entities == null) {
            return null;
        }

        List<WalletDto> list = new ArrayList<WalletDto>(entities.size());
        for (Wallet wallet : entities) {
            list.add(toModel(wallet));
        }
        return list;
    }


}
