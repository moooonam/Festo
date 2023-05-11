package com.example.festo.order.domain;

import com.example.festo.common.event.Event;
import lombok.Getter;

@Getter
public class OrderStatusChangedEvent extends Event {

    private final Long orderId;

    private final int orderNo;

    private final Long ordererId;

    private final Long boothOwnerId;

    private final String orderStatus;

    public OrderStatusChangedEvent(Long orderId, int orderNo, Long ordererId, Long boothOwnerId, String orderStatus) {
        super();
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.ordererId = ordererId;
        this.boothOwnerId = boothOwnerId;
        this.orderStatus = orderStatus;
    }
}
