package com.example.festo.product.application.port.in;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class RegisterProductCommand {

    private final Long requesterId;

    private final Long boothId;

    private final String productName;

    private final int price;

    private final MultipartFile productImage;

    @Builder
    public RegisterProductCommand(Long requesterId, Long boothId, String productName, int price, MultipartFile productImage) {
        this.requesterId = requesterId;
        this.boothId = boothId;
        this.productName = productName;
        this.price = price;
        this.productImage = productImage;
    }
}
