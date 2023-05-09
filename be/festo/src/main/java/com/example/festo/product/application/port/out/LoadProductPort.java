package com.example.festo.product.application.port.out;

import com.example.festo.product.domain.Product;

import java.util.List;

public interface LoadProductPort {

    List<Product> loadProductsByBoothId(Long boothId);
}
