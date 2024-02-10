package com.flapkap.vendingmachine.service;

import com.flapkap.vendingmachine.enums.Role;
import com.flapkap.vendingmachine.exception.custom.BadRequestException;
import com.flapkap.vendingmachine.exception.custom.NotAuthorizedException;
import com.flapkap.vendingmachine.model.Product;
import com.flapkap.vendingmachine.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ValidationService {

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    public void validateUser (Integer userId, Integer sellerId){
        log.info("Validate user method is called.");
        if (!userId.equals( sellerId)) {
            throw new NotAuthorizedException("User is not authorized");
        }
        User user = userService.getById(userId);
        if(!user.getRole().equals(Role.seller)){
            throw new NotAuthorizedException("User is not authorized");
        }
    }

    public void validateBuyer(Integer userId) {
        log.info("Validate buyer method is called.");
        User user = userService.getById(userId);
        if (!user.getRole().equals(Role.buyer)) {
            throw new NotAuthorizedException("User is not authorized");
        }
    }

    public void validateAmount(Integer amount , Integer productId) {
        log.info("Validate amount method is called.");
        Product product = productService.getById(productId);
        if (amount <= 0) {
            throw  new BadRequestException("Amount must be not zero or negative.");
        }
        if (amount > product.getAmountAvailable()) {
            throw  new BadRequestException("Amount of product available is less than requested.");
        }


    }

    public void validatePurchase(Integer userId , Integer productId, Integer amount) {
        log.info("Validate purchase method is called.");
        Product product = productService.getById(productId);
        User user = userService.getById(userId);
        if (user.getDeposit() < product.getTotalCost(amount)) {
            throw new BadRequestException("User don't have enough deposit.");
        }
    }

    public void validateCoin(Integer coin) {
        log.info("Validate coin method is called.");
        if (!(coin.equals(5) || coin.equals(10) || coin.equals(20)|| coin.equals(50)|| coin.equals(100)) ){
            throw new BadRequestException("The coins isn't valid");
        }
    }

    public void validateProductName(String proudctName) {
        log.info("Validate product name method is called.");
       final List<Product> allProduct = productService.getAll();

       for (Product product: allProduct){
           if (product.getProductName().equalsIgnoreCase(proudctName)){
               throw  new BadRequestException("This product name was inserted before.");
           }
       }

    }
    public void validateUserName(String userName) {
        log.info("Validate user name method is called.");
        if (userName.matches(".*\\d.*")) {
            throw  new BadRequestException("The user name must not have any number.");
        }

        for (User user: userService.getAll()){
            if (user.getUserName().equalsIgnoreCase(userName)){
                throw  new BadRequestException("This user name was inserted before.");
            }
        }

    }
    public void validateRole(Role role) {
        log.info("Validate role method is called.");
        if (role == null){
            throw new BadRequestException("The role isn't valid , it must be Buyer or Seller ");
        }
    }


}
