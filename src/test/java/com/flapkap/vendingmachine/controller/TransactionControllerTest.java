package com.flapkap.vendingmachine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flapkap.vendingmachine.dto.ReceiptDto;
import com.flapkap.vendingmachine.enums.Role;
import com.flapkap.vendingmachine.model.Product;
import com.flapkap.vendingmachine.model.User;
import com.flapkap.vendingmachine.service.TransactionService;
import com.flapkap.vendingmachine.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @Mock
    private ValidationService validationService;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController)
                .build();
    }

    @Test
    void givenValidUserIdAndCoin_whenDeposit_thenStatusOk() throws Exception {
        doNothing().when(validationService).validateBuyer(any());
        doNothing().when(validationService).validateCoin(any());
        doNothing().when(transactionService).deposit(any(), any());

        mockMvc.perform(post("/deposit")
                        .param("userId", "1")
                        .param("coin", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void givenValidUserId_whenReset_thenStatusOk() throws Exception {
        doNothing().when(validationService).validateBuyer(any());
        doNothing().when(transactionService).reset(any());

        mockMvc.perform(post("/reset")
                        .param("userId", "1"))
                .andExpect(status().isOk());
    }


}

