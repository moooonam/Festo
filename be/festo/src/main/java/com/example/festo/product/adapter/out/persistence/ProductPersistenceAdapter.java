package com.example.festo.product.adapter.out.persistence;

import com.example.festo.booth.adapter.out.persistence.BoothEntity;
import com.example.festo.booth.adapter.out.persistence.BoothRepository;
import com.example.festo.product.application.port.out.SaveProductPort;
import com.example.festo.product.domain.BoothInfo;
import com.example.festo.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements SaveProductPort {

    private final ProductRepository productRepository;

    private final BoothRepository boothRepository;

    @Override
    public Product saveProduct(Product product) {
        BoothEntity boothEntity = boothRepository.findById(product.getBoothInfo()
                                                                  .getBoothId())
                                                 .orElseThrow(NoSuchElementException::new);

        ProductEntity productEntity = ProductEntity.builder()
                                                   .productId(product.getProductId())
                                                   .price(product.getPrice())
                                                   .imageUrl(product.getProductImageUrl())
                                                   .name(product.getProductName())
                                                   .booth(boothEntity)
                                                   .build();

        productEntity = productRepository.save(productEntity);

        return mapToDomain(productEntity);
    }

    @Override
    public Long updateSetImg(Long productId, String imgUrl) {
        ProductEntity productEntity= productRepository.findById(productId).orElseThrow(NoSuchElementException::new);
        productEntity.setImageUrl(imgUrl);
        productRepository.save(productEntity);

        return productEntity.getProductId();
    }

    private Product mapToDomain(ProductEntity productEntity) {
        return Product.builder()
                      .productId(productEntity.getProductId())
                      .price(productEntity.getPrice())
                      .productImageUrl(productEntity.getImageUrl())
                      .boothInfo(new BoothInfo(productEntity.getBooth()))
                      .build();
    }
}
