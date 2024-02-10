package com.flapkap.vendingmachine.controller;


import com.flapkap.vendingmachine.model.Product;
import com.flapkap.vendingmachine.model.User;
import com.flapkap.vendingmachine.service.ProductService;
import com.flapkap.vendingmachine.service.ValidationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ValidationService validationService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getById(id));
    }
    @GetMapping("/all")
    public  ResponseEntity<List<Product>> getAll () {
        return ResponseEntity.ok(productService.getAll());
    }

    @PostMapping()
    public ResponseEntity<Integer> create(@RequestBody Product product,
                                          @RequestParam() Integer userId) {

        validationService.validateProductName(product.getProductName());
        validationService.validateUser(userId,product.getSellerId());
        return ResponseEntity.accepted().body(productService.create(product,userId));



    }
    @DeleteMapping ("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        productService.delete(id);
        return  ResponseEntity.noContent().build();
    }
    @PutMapping ()
    public ResponseEntity<Object> update(@Valid @RequestBody Product product,@RequestParam() Integer userId){
        validationService.validateUser(userId,product.getSellerId());
        validationService.validateProductName(product.getProductName());
        productService.update(product);
        return  ResponseEntity.noContent().build();
    }

}
