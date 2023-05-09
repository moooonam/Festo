package com.example.festo.order.adapter.in.web.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponse {

    private final String productName;

    private final int quantity;

}
