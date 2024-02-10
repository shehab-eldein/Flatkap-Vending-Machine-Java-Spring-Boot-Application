package com.flapkap.vendingmachine.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productTable")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Product name is required")
    private  String productName;
    private Integer amountAvailable=0;
    private  Integer cost=0;
    private  Integer sellerId;


    public  Integer getTotalCost(Integer amount) {
        return this.cost * amount;
    }
}
