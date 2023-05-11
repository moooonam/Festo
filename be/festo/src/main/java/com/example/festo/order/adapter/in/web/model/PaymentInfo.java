package com.example.festo.order.adapter.in.web.model;

import lombok.Getter;

@Getter
public class PaymentInfo {

    private final String paymentKey;

    private final String orderId;

    private final int amount;

    public PaymentInfo(String paymentKey, String orderId, int amount) {
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.amount = amount;
    }
}
