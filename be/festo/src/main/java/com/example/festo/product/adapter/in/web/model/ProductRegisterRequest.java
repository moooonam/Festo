package com.example.festo.product.adapter.in.web.model;

import lombok.Getter;

@Getter
public class ProductRegisterRequest {

    private final String productName;

    private final int price;

    public ProductRegisterRequest(String productName, int price) {
        this.productName = productName;
        this.price = price;
    }
}
