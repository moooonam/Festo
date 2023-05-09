package com.example.festo.order.domain;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OrderStatus {

    WAITING_ACCEPTANCE("주문 수락 대기", 0),
    COMPLETE_ACCEPTANCE("주문 수락 완료", 1),
    PREPARING_ORDER("상품 준비중", 2),
    READY_ORDER("상품 준비 완료", 3),
    WAITING_RECEIVE("수령 대기", 4),
    RECEIVE_COMPLETE("수령 완료", 5),
    COMPLETE("거래 완료", 6),

    CANCELED("취소", -1);

    private final String title;
    private final int number;

    OrderStatus(final String title, final int number) {
        this.title = title;
        this.number = number;
    }

    public static OrderStatus findBy(String value) {
        return Arrays.stream(values())
                     .filter(status -> status.name()
                                             .equals(value.toUpperCase()))
                     .findAny()
                     .orElseThrow(() -> new IllegalArgumentException("연산자를 찾을 수 없습니다."));
    }

    public boolean before(OrderStatus status) {
        return this.number + 1 == status.number;
    }
}
