package com.example.festo.order.application.port.out;

import com.example.festo.order.domain.Product;

public interface LoadProductPort {

    Product loadProduct(Long productId);
}
