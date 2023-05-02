package com.example.festo.order.domain;

public enum OrderStatus {

    WAITING_ACCEPTANCE("주문 수락 대기"),
    COMPLETE_ACCEPTANCE("주문 수락 완료"),
    PREPARING_ORDER("상품 준비중"),
    READY_ORDER("상품 준비 완료"),
    WAITING_RECEIVE("수령 대기"),
    RECEIVE_COMPLETE("수령 완료"),
    COMPLETE("거래 완료");

    OrderStatus(String value) {
    }
}
