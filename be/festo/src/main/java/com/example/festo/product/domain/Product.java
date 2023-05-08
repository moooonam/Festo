package com.example.festo.product.domain;

import com.example.festo.common.jpa.MoneyConverter;
import com.example.festo.common.model.Money;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long menuId;

    private String name;

    @Convert(converter = MoneyConverter.class)
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
