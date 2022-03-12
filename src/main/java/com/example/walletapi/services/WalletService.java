package com.example.walletapi.services;

import com.example.walletapi.dto.TransactionDto;
import com.example.walletapi.dto.WalletDto;
import com.example.walletapi.models.Currency;
import com.example.walletapi.models.TransactionType;
import java.util.List;

public interface WalletService {
    WalletDto create(Long id, Currency currency, String name);
    WalletDto getWallet(Long id);
    List<WalletDto> getAll();
    WalletDto topUp(Long id, TransactionDto dto);

    WalletDto sendFunds(Long id, String email, TransactionDto dto, Double amount);
}
