package com.flapkap.vendingmachine.service;

import com.flapkap.vendingmachine.dto.ReceiptDto;
import com.flapkap.vendingmachine.enums.Role;
import com.flapkap.vendingmachine.model.Product;
import com.flapkap.vendingmachine.model.User;
import com.flapkap.vendingmachine.repository.ProductRepository;
import com.flapkap.vendingmachine.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @Test
    void testDeposit() {
        User user = new User(1, "John", Role.buyer, "password", 0);
        when(userService.getById(1)).thenReturn(user);

        transactionService.deposit(1, 50);

        assertEquals(50, user.getDeposit());
        verify(userService).update(user);
    }

    @Test
    void testReset() {
        User user = new User(1, "John", Role.buyer, "password", 100);
        when(userService.getById(1)).thenReturn(user);

        transactionService.reset(1);

        assertEquals(0, user.getDeposit());
        verify(userService).update(user);
    }




}
