package com.flapkap.vendingmachine.controller;

import com.flapkap.vendingmachine.dto.ReceiptDto;
import com.flapkap.vendingmachine.service.TransactionService;
import com.flapkap.vendingmachine.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TransactionController {

    @Autowired
   private TransactionService transactionService;

    @Autowired
    private ValidationService validationService;


    @PostMapping("/deposit")
    public ResponseEntity<String> deposit (@RequestParam() Integer userId,
                                            @RequestParam() Integer coin) {

        validationService.validateBuyer(userId);
        validationService.validateCoin(coin);
        transactionService.deposit(userId,coin);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset")
    public ResponseEntity<String> reset (@RequestParam() Integer userId){

        validationService.validateBuyer(userId);
        transactionService.reset(userId);
        return ResponseEntity.ok().build();

    }
    
    @PostMapping("/buy")
    public  ResponseEntity<ReceiptDto> buy (@RequestParam() Integer userId, Integer productId, Integer amount) {

        validationService.validateBuyer(userId);
        validationService.validateAmount(amount, productId);
        validationService.validatePurchase(userId,productId,amount);

        return  ResponseEntity.ok(transactionService.buy(userId, productId, amount));
    }

}
