package com.flapkap.vendingmachine.service;
import com.flapkap.vendingmachine.enums.Role;
import com.flapkap.vendingmachine.exception.custom.BadRequestException;
import com.flapkap.vendingmachine.exception.custom.NotAuthorizedException;
import com.flapkap.vendingmachine.model.Product;
import com.flapkap.vendingmachine.model.User;
import com.flapkap.vendingmachine.service.ProductService;
import com.flapkap.vendingmachine.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ValidationServerTest {

    @InjectMocks
    private ValidationService validationService;

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @Test
    void testValidateUser_ValidUser() {
        User user = new User(1, "testName", Role.seller, "12345", 0);
        when(userService.getById(1)).thenReturn(user);

        validationService.validateUser(1, 1);

        verify(userService, times(1)).getById(1);
    }
    @Test
    void testValidateAmount_InvalidAmount() {
        Product product = new Product(1, "Pepsi", 20, 3, 4);
        when(productService.getById(1)).thenReturn(product);

        assertThrows(BadRequestException.class, () -> validationService.validateAmount(0, 1));

        verify(productService, times(1)).getById(1);
    }
    @Test
    void testValidateUser_InvalidUser() {
        User user = new User(1, "testName", Role.buyer, "12345", 0);
       // when(userService.getById(1)).thenReturn(user);
        assertThrows(NotAuthorizedException.class, () -> validationService.validateUser(1, 2));

    }

    @Test
    void testValidateRole_InvalidRole() {
        assertThrows(BadRequestException.class, () -> validationService.validateRole(null));
    }
    @Test
    void testValidateRole_ValidRole() {
        assertDoesNotThrow(() -> validationService.validateRole(Role.buyer));
    }

    @Test
    void testValidateCoin_InvalidCoin() {
        assertThrows(BadRequestException.class, () -> validationService.validateCoin(25));
    }



}
