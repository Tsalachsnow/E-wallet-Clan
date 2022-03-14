package com.example.walletapi.controllers;




import com.example.walletapi.dto.*;
import com.example.walletapi.services.WalletService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Wallet")
@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @ApiOperation(value = "transfer a funds to a Customer")
    @PutMapping("/transfer")
    public WalletDto transfer( @RequestBody TransferDto dto) throws Exception {
        return walletService.sendFunds(dto);
    }

    @ApiOperation(value = "TopUp / Withdraw from a Wallet by Wallet by acc number and email ")
    @PutMapping("/update")
    public ResponseEntity<TopUpResponseDto> updateBalance(@RequestBody TopUpDto dto) {
        return ResponseEntity.ok(walletService.topUp(dto));
    }
    @ApiOperation(value = "account upgrade")
    @PostMapping("/KycLevel")
    public ResponseEntity<KycLevelDto> upgradeAcc(Long id){
        return ResponseEntity.ok(walletService.updateLevel(id));
    }
}
