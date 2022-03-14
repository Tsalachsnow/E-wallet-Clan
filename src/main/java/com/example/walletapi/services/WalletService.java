package com.example.walletapi.services;

import com.example.walletapi.dto.*;
import java.util.List;

public interface WalletService {
TopUpResponseDto topUp(TopUpDto dto);
    KycLevelDto updateLevel(long id);
WalletDto sendFunds(TransferDto dto) throws Exception;
}
