package com.example.walletapi.controllers;

import com.example.walletapi.dto.CustomerDto;
import com.example.walletapi.dto.CustomerResponse;
import com.example.walletapi.services.CustomerService;
import com.example.walletapi.util.AuthenticationRequest;
import com.example.walletapi.util.AuthenticationResponse;
import java.io.IOException;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/userwallet/{email}")
    public ResponseEntity<?> findUser(@PathVariable("email") String email){
        return ResponseEntity.ok(customerService.getWalletUsers(email));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register (@Valid @RequestBody CustomerDto customer) throws IOException {
//        personRequest.setRoleDetail(ROLE_DETAIL.PREMIUM);
        return ResponseEntity.ok(customerService.register(customer));
    }
    @GetMapping("/profile")
    public ResponseEntity<CustomerResponse> getUserInfo() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("AM i getting here??");
        return ResponseEntity.ok().body(customerService.getInfo(authentication));
    }

    @PostMapping(path="/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest req) throws Exception {
        return customerService.loginUser(req);
    }
}
