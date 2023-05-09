package com.example.festo.order.adapter.in.web.model;

import com.example.festo.order.domain.*;
import lombok.Getter;

@Getter
public class OrderSummary {

    private String festivalName;

    private OrderNo orderNo;

    private Long boothId;

    private String boothName;

    private String imageUrl;

    private OrderStatus orderStatus;

    private String productName;

    private int etcCount;

    public OrderSummary(Order order, FestivalInfo festivalInfo, Product firstProduct) {
        this.festivalName = festivalInfo.getFestivalName();
        this.orderNo = order.getOrderNo();
        this.boothId = order.getBoothInfo().getBoothId();
        this.boothName = order.getBoothInfo().getBoothName();
        this.imageUrl = festivalInfo.getImageUrl();
        this.orderStatus = order.getOrderStatus();
        this.productName = firstProduct.getName();
        this.etcCount = order.getOrderLines().size() - 1;
    }
}