package com.example.festo.order.domain;

import com.example.festo.common.model.Money;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Product {

    private Long id;

    private Long menuId;

    private String name;

    private Money price;

    private String description;

    private String imageUrl;

    @Builder
    public Product(Long id, Long menuId, String name, Money price, String description, String imageUrl) {
        this.id = id;
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
