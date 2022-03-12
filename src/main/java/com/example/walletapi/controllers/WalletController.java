package com.example.walletapi.controllers;




import com.example.walletapi.dto.TransactionDto;
import com.example.walletapi.dto.WalletDto;
import com.example.walletapi.models.Currency;
import com.example.walletapi.models.TransactionType;
import com.example.walletapi.services.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Wallet")
@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @ApiOperation(value = "Create a Wallet for a Customer by Customer id")
    @PostMapping("/create")
    public WalletDto create(@RequestParam(name = "id") Long id, @RequestParam Currency currency, @RequestParam String name) {
        return walletService.create(id, currency, name);
    }

    @ApiOperation(value = "Get Wallet by Wallet id")
    @GetMapping("/{id}")
    public WalletDto getWallet(@PathVariable("id") Long id) {
        return walletService.getWallet(id);
    }

    @PutMapping("/transfer/{id}/{email}")
    public WalletDto transfer(@PathVariable("id") Long id, @PathVariable("email") String email, @RequestBody TransactionDto dto, @RequestParam Double amount){
        return walletService.sendFunds(id, email, dto, amount);
    }

    @ApiOperation(value = "TopUp / Withdraw from a Wallet by Wallet id ")
    @PutMapping("/update/{id}")
    public ResponseEntity<WalletDto> updateBalance(@PathVariable("id") Long id, @RequestBody TransactionDto dto) {
        return ResponseEntity.ok().body(WalletService.topUp(id, dto));
    }

    @ApiOperation(value = "Get All Wallets")
    @GetMapping("/list")
    public List<WalletDto> getAll() {
        return walletService.getAll();
    }
}
