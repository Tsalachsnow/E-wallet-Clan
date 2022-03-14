package com.example.walletapi.services;

import com.example.walletapi.dto.CustomerDto;
import com.example.walletapi.dto.CustomerResponse;
import com.example.walletapi.dto.WalletDto;
import com.example.walletapi.models.Customer;
import com.example.walletapi.util.AuthenticationRequest;
import com.example.walletapi.util.AuthenticationResponse;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface CustomerService {
    CustomerResponse register(CustomerDto personRequest) throws IOException;
    ResponseEntity<AuthenticationResponse> loginUser(AuthenticationRequest req) throws Exception;
    CustomerResponse getInfo(Authentication auth);
//    String getUserNameFromSecurityContext();
    Customer getUserFromSecurityContext();
    CustomerResponse getWalletUsers(String email);
}
