package com.example.festo.order.adapter.in.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct {

    private Long productId;

    private Integer quantity;
}
