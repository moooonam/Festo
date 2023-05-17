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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Convert(converter = MoneyConverter.class)
    private Money price;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "booth_id", nullable = false)
    private BoothEntity booth;

    @Builder
    public ProductEntity(Long productId, String name, Money price, String imageUrl, BoothEntity booth) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.booth = booth;
    }

    public void setImageUrl(String imgUrl) {
        this.imageUrl = imgUrl;
    }
}
