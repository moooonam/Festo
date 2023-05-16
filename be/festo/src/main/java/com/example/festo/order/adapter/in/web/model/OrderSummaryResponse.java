package com.example.festo.order.adapter.in.web.model;

import com.example.festo.order.domain.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd/HH:mm")
    private final LocalDateTime time;

    public OrderSummaryResponse(Order order, FestivalInfo festivalInfo, Menu firstMenu, LocalDateTime time) {
        this.festivalName = festivalInfo.getFestivalName();
        this.orderNo = order.getOrderNo();
        this.boothId = order.getBoothInfo().getBoothId();
        this.boothName = order.getBoothInfo().getBoothName();
        this.imageUrl = festivalInfo.getImageUrl();
        this.orderStatus = order.getOrderStatus();
        this.orderId = order.getOrderId();
        this.productName = firstMenu.getName();
        this.etcCount = order.getOrderLines().size() - 1;
        this.time = time;
    }
}