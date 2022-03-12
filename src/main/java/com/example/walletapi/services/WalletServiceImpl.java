package com.example.walletapi.services;

import com.example.walletapi.dto.TransactionDto;
import com.example.walletapi.dto.WalletDto;
import com.example.walletapi.dto.WalletResponseDto;
import com.example.walletapi.models.*;
import com.example.walletapi.repositories.CustomerRepository;
import com.example.walletapi.repositories.TransactionRepository;
import com.example.walletapi.repositories.WalletRepository;
import com.example.walletapi.services.exception.types.ResourceNotFound;
import com.example.walletapi.services.exception.types.TransactionNotAllowed;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService{

    private final ModelMapper modelMapper;

    private final WalletRepository walletRepository;
    private WalletResponseDto walletResponseDto;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public WalletDto create(Long id, Currency currency, String name) {
        WalletDto walletDto = new WalletDto();
        Wallet wallet = new Wallet();
        walletDto.setName(walletDto.getName());
        walletDto.setCurrency(walletDto.getCurrency());
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFound(id, Customer.class));
        walletDto.setCustomer(customer);
        walletRepository.save(wallet);
        wallet = walletRepository.save(modelMapper.map(walletDto, Wallet.class));
        walletResponseDto = modelMapper.map(wallet, WalletResponseDto.class);
        return walletResponseDto.toModel(wallet);
    }

    public WalletDto getWallet(Long id) {
        return walletResponseDto.toModel(walletRepository.findById(id).orElseThrow(() -> new ResourceNotFound(id, Wallet.class)));
    }

    public List<WalletDto> getAll() {
        return walletResponseDto.toModel(walletRepository.findAll());
    }


    public WalletDto sendFunds(Long id, String email, WalletDto dto, Double amount) {
        Wallet wallet = new Wallet();
        Customer customer = customerRepository.findCustomersByEmailAndId(email, id).orElseThrow(() -> new ResourceNotFound((email), Customer.class));
        if(dto.getCustomer().equals(customer) && amount <= wallet.getTransactionLimit()){
            dto.setBalance(dto.getBalance() + amount);
            wallet = walletRepository.save(modelMapper.map(dto, Wallet.class));
            walletResponseDto = modelMapper.map(wallet, WalletResponseDto.class);
            return walletResponseDto.toModel(wallet);
        }else{
            return
        }
    }
    public WalletDto topUp(Long id,  TransactionDto dto) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new ResourceNotFound(id, Wallet.class));
        Double amount = dto.getAmount();
        if (dto.getTransactionType() == TransactionType.CREDIT) {
            amount = Math.abs(amount);
        }
        else if (dto.getTransactionType() == TransactionType.DEBIT) {
            amount = -1 * Math.abs(amount);
        }
        if (wallet.getBalance() + amount >= 0) {
            wallet.setBalance(wallet.getBalance() + amount);
        }
        else {
            throw new TransactionNotAllowed(id, "Insufficient Funds", Wallet.class);
        }
        walletRepository.save(wallet);
        transactionRepository.save(modelMapper.map(dto, Transaction.class));
        return walletResponseDto.toModel(wallet);
    }

}
