package com.flapkap.vendingmachine.dto;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;




@Builder
@Data
public class ReceiptDto {

    private Integer totalCost;
    private  Integer productAmount;
    private  String productName;
    private  Integer change;




}
