package com.example.festo.order.domain;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OrderStatus {

    WAITING_ACCEPTANCE("주문 수락 대기", 0),
    PREPARING_ORDER("상품 준비중", 1),
    WAITING_RECEIVE("수령 대기", 2),
    COMPLETE("거래 완료", 3),

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
