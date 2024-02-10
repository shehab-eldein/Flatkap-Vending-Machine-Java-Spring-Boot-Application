package com.flapkap.vendingmachine.service;


import com.flapkap.vendingmachine.dto.ReceiptDto;
import com.flapkap.vendingmachine.model.Product;
import com.flapkap.vendingmachine.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;


  public void deposit (Integer userId , Integer coin) {

      log.info("Deposit method is called.");
        User user = userService.getById(userId);

        user.setDeposit(user.getDeposit() + coin);
        userService.update(user);
    }
    public void reset (Integer userId) {
        log.info("Reset deposit method by userId is called.");
        User user = userService.getById(userId);
        user.setDeposit(0);
        userService.update(user);
    }

    public ReceiptDto buy(Integer userId , Integer productId, Integer amount) {
        log.info(" Buy method  is called.");

        User user = userService.getById(userId);
        Product product = productService.getById(productId);
        Integer change = user.getDeposit() - product.getTotalCost(amount);
        product.setAmountAvailable(product.getAmountAvailable() - amount);
        productService.update(product);
        user.setDeposit(0);
        userService.update(user);

        System.out.println("All Accepted ________");

        return ReceiptDto.builder()
                .productAmount(amount)
                .productName(product.getProductName())
                .totalCost(product.getTotalCost(amount))
                .change(change)
                .build();




    }










}
