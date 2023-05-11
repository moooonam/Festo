package com.example.festo.order.adapter.in.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderDetailResponse {

    private final int orderNo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd/HH:mm", timezone = "Asia/Seoul")
    private final LocalDateTime time;

    private final int totalPrice;

    private final List<MenuResponse> menus;
}
