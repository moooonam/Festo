package com.example.festo.product.application.service;

import com.example.festo.common.model.Money;
import com.example.festo.product.adapter.in.web.model.ProductResponse;
import com.example.festo.product.application.port.in.LoadProductUseCase;
import com.example.festo.product.application.port.in.RegisterProductCommand;
import com.example.festo.product.application.port.in.RegisterProductUseCase;
import com.example.festo.product.application.port.out.LoadBoothInfoPort;
import com.example.festo.product.application.port.out.LoadProductPort;
import com.example.festo.product.application.port.out.SaveImgPort;
import com.example.festo.product.application.port.out.SaveProductPort;
import com.example.festo.product.domain.BoothInfo;
import com.example.festo.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductService implements RegisterProductUseCase, LoadProductUseCase {

    private final SaveImgPort saveImgPort;

    private final SaveProductPort saveProductPort;

    private final LoadBoothInfoPort loadBoothInfoPort;

    private final LoadProductPort loadProductPort;

    @Override
    public Long registerProduct(RegisterProductCommand registerProductCommand) {

        BoothInfo boothInfo = loadBoothInfoPort.loadBoothInfo(registerProductCommand.getBoothId());

        if (!boothInfo.isOwner(registerProductCommand.getRequesterId())) {
            throw new AuthorizationServiceException("부스 관리자만 상품을 등록할 수 있습니다.");
        }

        Product product = Product.builder()
                                 .productName(registerProductCommand.getProductName())
                                 .price(new Money(registerProductCommand.getPrice()))
                                 .boothInfo(boothInfo)
                                 .build();

        product = saveProductPort.saveProduct(product);
        String imageUrl = saveImgPort.saveProductImg(registerProductCommand.getProductImage(), product.getProductId());

        return saveProductPort.updateSetImg(product.getProductId(), imageUrl);
    }

    @Override
    public List<ProductResponse> loadProductsByBoothId(Long boothId) {

        List<Product> products = loadProductPort.loadProductsByBoothId(boothId);

        return products.stream()
                       .map(product -> new ProductResponse(
                               product.getProductId(),
                               product.getProductImageUrl(),
                               product.getProductName(),
                               product.getPrice().getValue()))
                       .collect(Collectors.toList());
    }
}
