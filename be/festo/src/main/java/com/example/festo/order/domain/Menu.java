package com.example.festo.order.domain;

import com.example.festo.common.model.Money;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Menu {

    private Long menuId;

    private String name;

    private Money price;

    private String imageUrl;

    @Builder
    public Menu(Long menuId, String name, Money price, String imageUrl) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
