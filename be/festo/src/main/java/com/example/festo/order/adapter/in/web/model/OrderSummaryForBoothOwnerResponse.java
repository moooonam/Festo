package com.example.festo.order.adapter.in.web.model;

import com.example.festo.order.domain.Order;
import com.example.festo.order.domain.OrderNo;
import com.example.festo.order.domain.OrderStatus;
import com.example.festo.order.domain.Menu;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderSummaryForBoothOwnerResponse {

    private final OrderNo orderNo;

    private final OrderStatus orderStatus;

    private final LocalDateTime time;

    private final String firstMenuName;

    private final int etcCount;

    public OrderSummaryForBoothOwnerResponse(Order order, Menu firstMenu) {
        this.orderNo = order.getOrderNo();
        this.orderStatus = order.getOrderStatus();
        this.time = order.getOrderTime();
        this.firstMenuName = firstMenu.getName();
        this.etcCount = order.getOrderLines().size() - 1;
    }
}
