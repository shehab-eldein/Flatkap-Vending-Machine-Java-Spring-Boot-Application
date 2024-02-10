package com.flapkap.vendingmachine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flapkap.vendingmachine.enums.Role;
import com.flapkap.vendingmachine.model.Product;
import com.flapkap.vendingmachine.service.ProductService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private ValidationService validationService;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .build();
    }

    @Test
    void givenValidProductId_whenGetById_thenReturnProduct() throws Exception {
        Product product = new Product(1, "Pepsi", 20, 3, 4);
        when(productService.getById(1)).thenReturn(product);

        MvcResult mvcResult = mockMvc.perform(get("/product/1"))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        Product productResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Product.class);

        assertEquals(productResult.getId(), product.getId());
        assertEquals(productResult.getProductName(), product.getProductName());
    }

    @Test
    void whenGetAll_thenReturnProductList() throws Exception {
        List<Product> productList = List.of(
                new Product(1, "Pepsi", 20, 3, 4),
                new Product(2, "Cola", 10, 10, 3)
        );
        when(productService.getAll()).thenReturn(productList);

        MvcResult mvcResult = mockMvc.perform(get("/product/all"))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        List<Product> result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);

        assertEquals(productList.size(), result.size());
    }

    @Test
    void givenValidProductObjectAndUserId_whenCreateProduct_thenProductIsCreated() throws Exception {
        Product product = new Product(1, "Pepsi", 20, 3, 4);
        when(productService.create(any(Product.class), any())).thenReturn(1);

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult mvcResult = mockMvc.perform(post("/product")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isAccepted())
                .andReturn();

        Integer result = Integer.valueOf(mvcResult.getResponse().getContentAsString());

        assertEquals(result, product.getId());
    }

    @Test
    void givenValidProductId_whenDeleteProduct_thenNoContent() throws Exception {
        doNothing().when(productService).delete(1);

        mockMvc.perform(delete("/product/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void givenValidProductObjectAndUserId_whenUpdateProduct_thenNoContent() throws Exception {
        Product product = new Product(1, "Pepsi", 20, 3, 4);
        doNothing().when(productService).update(any(Product.class));

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(put("/product")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isNoContent());
    }
}

