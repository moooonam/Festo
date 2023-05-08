package com.example.festo.order.adapter.out.persistence;

import com.example.festo.order.domain.Product;
import com.example.festo.product.adapter.out.persistence.ProductRepository;
import com.example.festo.product.application.port.out.LoadProductPort;
import com.example.festo.product.domain.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements LoadProductPort {

    private final ProductRepository productRepository;

    @Override
    public Product loadProduct(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
                                                       .orElseThrow(NoSuchElementException::new);

        return mapToProductDomain(productEntity);
    }

    private Product mapToProductDomain(ProductEntity productEntity) {
        return Product.builder()
                      .id(productEntity.getId())
                      .menuId(productEntity.getMenuId())
                      .price(productEntity.getPrice())
                      .name(productEntity.getName())
                      .description(productEntity.getDescription())
                      .imageUrl(productEntity.getImageUrl())
                      .build();
    }
}
