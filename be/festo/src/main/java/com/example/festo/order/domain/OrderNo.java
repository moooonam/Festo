package com.example.festo.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderNo implements Serializable {

    @Column(name = "order_number", nullable = false)
    private Integer number;

    protected OrderNo() {
    }

    public OrderNo(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderNo orderNo = (OrderNo) o;
        return Objects.equals(number, orderNo.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    public static OrderNo of(int number) {
        return new OrderNo(number);
    }
}
