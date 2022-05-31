package com.soa.shop.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

    private int productId;
    private String productName;
    private double price;
    private String description;
    private int quantity;
    private String photo;
}
