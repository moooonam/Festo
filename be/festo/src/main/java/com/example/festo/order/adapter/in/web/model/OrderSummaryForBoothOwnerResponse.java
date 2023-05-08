package com.example.festo.order.adapter.in.web.model;

import com.example.festo.order.domain.Order;
import com.example.festo.order.domain.OrderNo;
import com.example.festo.order.domain.OrderStatus;
import com.example.festo.order.domain.Product;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderSummaryForBoothOwnerResponse {

    private final OrderNo orderNo;

    private final OrderStatus orderStatus;

    private final LocalDateTime time;

    private final String firstProductName;

    private final int etcCount;

    public OrderSummaryForBoothOwnerResponse(Order order, Product firstProduct) {
        this.orderNo = order.getOrderNo();
        this.orderStatus = order.getOrderStatus();
        this.time = order.getOrderTime();
        this.firstProductName = firstProduct.getName();
        this.etcCount = order.getOrderLines().size() - 1;
    }
}
