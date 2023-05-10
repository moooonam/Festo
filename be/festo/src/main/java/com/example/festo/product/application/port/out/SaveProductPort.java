package com.example.festo.product.application.port.out;

import com.example.festo.product.domain.Product;

public interface SaveProductPort {

    Product saveProduct(Product product);

    Long updateSetImg(Long boothId,String imgUrl);
}
