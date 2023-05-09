package com.example.festo.product.adapter.in.web.model;

import lombok.Getter;

@Getter
public class ProductResponse {

    private final Long productId;

    private final String imageUrl;

    private final String name;

    private final int price;

    public ProductResponse(Long productId, String imageUrl, String name, int price) {
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
    }
}
