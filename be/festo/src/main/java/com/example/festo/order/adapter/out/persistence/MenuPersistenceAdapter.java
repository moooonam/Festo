package com.example.festo.order.adapter.out.persistence;

import com.example.festo.order.domain.Menu;
import com.example.festo.product.adapter.out.persistence.ProductRepository;
import com.example.festo.order.application.port.out.LoadMenuPort;
import com.example.festo.product.adapter.out.persistence.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class MenuPersistenceAdapter implements LoadMenuPort {

    private final ProductRepository productRepository;

    @Override
    public Menu loadMenu(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
                                                       .orElseThrow(NoSuchElementException::new);

        return mapToMenuDomain(productEntity);
    }

    private Menu mapToMenuDomain(ProductEntity productEntity) {
        return Menu.builder()
                   .menuId(productEntity.getProductId())
                   .price(productEntity.getPrice())
                   .name(productEntity.getName())
                   .imageUrl(productEntity.getImageUrl())
                   .build();
    }
}
