package com.example.festo.order.domain;


import com.example.festo.common.jpa.MoneyConverter;
import com.example.festo.common.model.Money;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class OrderLine {

    private Long productId;

    @Convert(converter = MoneyConverter.class)
    @Column(name = "price")
    private Money price;

    private int quantity;

    @Convert(converter = MoneyConverter.class)
    @Column(name = "amounts")
    private Money amounts;

    protected OrderLine() {
    }

    public OrderLine(Long productId, Money price, int quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.amounts = calculateAmounts();
    }

    private Money calculateAmounts() {
        return this.price.multiply(quantity);
    }
}
