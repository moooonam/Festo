package com.example.festo.product.adapter.in.web.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ProductRegisterRequest {

    @NotBlank(message = "[Request] productName는 빈 값일 수 없습니다.")
    private final String productName;

    @NotBlank(message = "[Request] price는 빈 값일 수 없습니다.")
    private final int price;

    public ProductRegisterRequest(String productName, int price) {
        this.productName = productName;
        this.price = price;
    }
}
