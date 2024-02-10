package com.flapkap.vendingmachine.service;

import com.flapkap.vendingmachine.model.Product;
import com.flapkap.vendingmachine.model.User;
import com.flapkap.vendingmachine.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductSerciveTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserService userService;



    @Test
    void givenProductId_WhenGetById_ThenReturnProduct() {
        Product product = new Product(1, "Pepsi", 20, 3, 4);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Product result = productService.getById(1);

        assertEquals(product, result);
    }

    @Test
    void givenInvalidProductId_WhenGetById_ThenThrowRuntimeException() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.getById(1));
    }

    @Test
    void whenGetAll_ThenReturnProductList() {
        List<Product> productList = Arrays.asList(
                new Product(1, "Pepsi", 20, 3, 4),
                new Product(2, "Cola", 10, 10, 3)
        );
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.getAll();

        assertEquals(productList.size(), result.size());
        assertEquals(productList, result);
    }
    @Test
    void givenProductId_WhenDelete_ThenNoExceptionThrown() {
        doNothing().when(productRepository).deleteById(1);

        assertDoesNotThrow(() -> productService.delete(1));
    }


    @Test
    void givenInvalidProduct_WhenUpdate_ThenThrowRuntimeException() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.update(new Product()));
    }


}
