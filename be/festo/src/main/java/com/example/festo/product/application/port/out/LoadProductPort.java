package com.example.festo.product.application.port.out;

import com.example.festo.order.domain.Product;

public interface LoadProductPort {

    Product loadProduct(Long productId);
}
