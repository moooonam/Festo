package com.example.festo.order.adapter.in.web.model;

import com.example.festo.order.domain.*;
import lombok.Getter;

@Getter
public class OrderSummaryResponse {


    private final String festivalName;

    private final OrderNo orderNo;

    private final Long boothId;

    private final String boothName;

    private final String imageUrl;

    private final Long orderId;

    private final OrderStatus orderStatus;

    private final String productName;

    private final int etcCount;

    public OrderSummaryResponse(Order order, FestivalInfo festivalInfo, Menu firstMenu) {
        this.festivalName = festivalInfo.getFestivalName();
        this.orderNo = order.getOrderNo();
        this.boothId = order.getBoothInfo().getBoothId();
        this.boothName = order.getBoothInfo().getBoothName();
        this.imageUrl = festivalInfo.getImageUrl();
        this.orderStatus = order.getOrderStatus();
        this.orderId = order.getOrderId();
        this.productName = firstMenu.getName();
        this.etcCount = order.getOrderLines().size() - 1;
    }
}