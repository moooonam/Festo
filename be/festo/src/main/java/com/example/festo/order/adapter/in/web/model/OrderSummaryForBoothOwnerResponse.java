package com.example.festo.order.adapter.in.web.model;

import com.example.festo.order.domain.Order;
import com.example.festo.order.domain.OrderNo;
import com.example.festo.order.domain.OrderStatus;
import com.example.festo.order.domain.Menu;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderSummaryForBoothOwnerResponse {

    private final Long orderId;

    private final OrderNo orderNo;

    private final OrderStatus orderStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd/HH:mm", timezone = "Asia/Seoul")
    private final LocalDateTime time;

    private final String firstMenuName;

    private final int etcCount;

    public OrderSummaryForBoothOwnerResponse(Order order, Menu firstMenu, int count) {
        this.orderId = order.getOrderId();
        this.orderNo = order.getOrderNo();
        this.orderStatus = order.getOrderStatus();
        this.time = order.getOrderTime();
        this.firstMenuName = firstMenu.getName();
        this.etcCount = count;
    }
}
