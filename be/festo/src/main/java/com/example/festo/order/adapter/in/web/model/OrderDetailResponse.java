package com.example.festo.order.adapter.in.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderDetailResponse {

    private final int orderNo;

    private final LocalDateTime time;

    private final int totalPrice;

    private final List<MenuResponse> menus;
}
