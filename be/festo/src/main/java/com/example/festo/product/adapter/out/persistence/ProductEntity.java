package com.example.festo.product.adapter.out.persistence;

import com.example.festo.booth.adapter.out.persistence.BoothEntity;
import com.example.festo.common.jpa.MoneyConverter;
import com.example.festo.common.model.Money;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String name;

    @Convert(converter = MoneyConverter.class)
    private Money price;

    private String description;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "booth_id")
    private BoothEntity booth;

    @Builder
    public ProductEntity(Long productId, String name, Money price, String description, String imageUrl, BoothEntity booth) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.booth = booth;
    }

    public void setImageUrl(String imgUrl) {
        this.imageUrl = imgUrl;
    }
}
