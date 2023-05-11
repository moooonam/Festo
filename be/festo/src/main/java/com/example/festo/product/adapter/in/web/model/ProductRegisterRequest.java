package com.example.festo.product.adapter.in.web.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ProductRegisterRequest {

    @NotBlank(message = "[Request] productName는 빈 값일 수 없습니다.")
    private final String productName;

    @Min(value = 0, message = "[Request] price는 최소 0 이상이어야합니다.")
    @NotBlank(message = "[Request] price는 빈 값일 수 없습니다.")
    private final int price;

    public ProductRegisterRequest(String productName, int price) {
        this.productName = productName;
        this.price = price;
    }
}
