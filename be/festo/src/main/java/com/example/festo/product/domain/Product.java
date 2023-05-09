package com.example.festo.product.domain;

import com.example.festo.common.model.Money;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Product {

    private Long productId;

    private String productName;

    private Money price;

    private String productImageUrl;

    private BoothInfo boothInfo;

    @Builder
    public Product(Long productId, String productName, Money price, String productImageUrl, BoothInfo boothInfo) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.productImageUrl = productImageUrl;
        this.boothInfo = boothInfo;
    }
}
