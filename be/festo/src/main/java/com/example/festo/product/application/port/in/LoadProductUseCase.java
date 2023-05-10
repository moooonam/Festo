package com.example.festo.product.application.port.in;

import com.example.festo.product.adapter.in.web.model.ProductResponse;

import java.util.List;

public interface LoadProductUseCase {

    List<ProductResponse> loadProductsByBoothId(Long boothId);
}
