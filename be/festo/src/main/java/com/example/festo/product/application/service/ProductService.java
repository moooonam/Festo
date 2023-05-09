package com.example.festo.product.application.service;

import com.example.festo.common.model.Money;
import com.example.festo.product.application.port.in.RegisterProductCommand;
import com.example.festo.product.application.port.in.RegisterProductUseCase;
import com.example.festo.product.application.port.out.LoadBoothInfoPort;
import com.example.festo.product.application.port.out.SaveImgPort;
import com.example.festo.product.application.port.out.SaveProductPort;
import com.example.festo.product.domain.BoothInfo;
import com.example.festo.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductService implements RegisterProductUseCase {

    private final SaveImgPort saveImgPort;

    private final SaveProductPort saveProductPort;

    private final LoadBoothInfoPort loadBoothInfoPort;

    @Override
    public Long registerProduct(RegisterProductCommand registerProductCommand) {

        BoothInfo boothInfo = loadBoothInfoPort.loadBoothInfo(registerProductCommand.getBoothId());

        if (boothInfo.isOwner(registerProductCommand.getRequesterId())) {
            throw new RuntimeException("권한 없음");
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
}
