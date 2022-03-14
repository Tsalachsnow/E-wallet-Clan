package com.example.walletapi.services;

import com.example.walletapi.dto.*;
import com.example.walletapi.models.*;
import com.example.walletapi.repositories.CustomerRepository;
import com.example.walletapi.repositories.TransactionRepository;
import com.example.walletapi.repositories.WalletRepository;
import com.example.walletapi.security.SecurityConfiguration;
import com.example.walletapi.services.exception.types.ResourceNotFound;
import com.example.walletapi.services.exception.types.TransactionNotAllowed;
import java.awt.color.ProfileDataException;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService{

    private final ModelMapper modelMapper;
    private final CustomerService customerService;
    private final WalletRepository walletRepository;
    private WalletResponseDto walletResponseDto;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

@Override
    public WalletDto sendFunds(TransferDto dto) throws Exception {

      // business logic for transfering money from a current logged in user to a customer
        try{
            Customer currentPerson= customerService.getUserFromSecurityContext();
            Wallet wallet = currentPerson.getWallets();
            if (wallet.getBalance() >= dto.getAmount() && dto.getAmount() <= currentPerson.getLimit()){
                wallet.setBalance(wallet.getBalance() - dto.getAmount());
            walletRepository.save(wallet);
            }else{
                throw new ResourceNotFound("transfer limit exceeded" + "limit: "+ currentPerson.getLimit());
            }
            Customer customer = customerRepository.findCustomersByEmailAndWalletsAcc(dto.getEmail(), dto.getAcc()).orElseThrow(() -> new ProfileDataException("Account does not exist"));
            Double customerBalance = customer.getWallets().getBalance();
            customerBalance += dto.getAmount();
            customer.getWallets().setBalance(customerBalance);
            walletRepository.save(modelMapper.map(customer.getWallets(), Wallet.class));

            walletResponseDto = modelMapper.map(wallet, WalletResponseDto.class);
           return walletResponseDto.toModel(wallet);
        } catch (Exception e) {
            throw new Exception("transfer limit exceeded", e);
        }
    }
    //to topUp or withdraw wallet funds
    @Override
    public TopUpResponseDto topUp(TopUpDto dto1) {
        TopUpResponseDto responseDto;
        Transaction transaction;
        Customer customer = customerService.getUserFromSecurityContext();
        Wallet wallet1 = walletRepository.findByAcc(dto1.getAcc()).orElseThrow(() -> new ResourceNotFound("Wallet acc not found"));
        Double amount = dto1.getAmount();

        String transactionType = dto1.getTransactionType();
        if (Objects.equals(TransactionType.CREDIT, TransactionType.valueOf(transactionType.toUpperCase()))) {
            wallet1.setBalance(wallet1.getBalance() + amount);
           walletRepository.save(wallet1);

        }
        else if (Objects.equals(TransactionType.DEBIT, TransactionType.valueOf(transactionType.toUpperCase()))) {
            if(amount <= customer.getLimit()){
                wallet1.setBalance(wallet1.getBalance() - amount);
                walletRepository.save(wallet1);
            }
        }
        else {
            throw new ResourceNotFound("Insufficient Funds");
        }
        transaction=transactionRepository.save(modelMapper.map(dto1, Transaction.class));
        responseDto = modelMapper.map(transaction, TopUpResponseDto.class);
        return responseDto;
    }
    //to upgrade an existing account from normal to premium which affects the transaction limit
   @Override
    public KycLevelDto updateLevel(long id){
        Customer customer = customerRepository.getById(id);
        if(customer.getLevel().equals(KycLevel.NORMAL)){
            customer.setLevel(KycLevel.PREMIUM);
            customerRepository.save(customer);
        }
        return KycLevelDto.builder().message("your Account has been successfully Upgraded").build();
    }


}
