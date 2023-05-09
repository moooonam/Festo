package com.example.festo.product.adapter.in.web;

import com.example.festo.product.adapter.in.web.model.ProductRegisterRequest;
import com.example.festo.product.application.port.in.RegisterProductCommand;
import com.example.festo.product.application.port.in.RegisterProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final RegisterProductUseCase registerProductUsecase;

    @PostMapping("/booths/{booth_id}/menu")
    public ResponseEntity<Long> addMenu(
            @PathVariable("booth_id") Long boothId,
            @RequestPart("request") ProductRegisterRequest request,
            @RequestPart("productImage") MultipartFile productImage) {

        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                                                              .getAuthentication()
                                                              .getPrincipal();

        RegisterProductCommand registerProductCommand = RegisterProductCommand.builder()
                                                                              .requesterId(Long.parseLong(user.getUsername()))
                                                                              .boothId(boothId)
                                                                              .productName(request.getProductName())
                                                                              .price(request.getPrice())
                                                                              .productImage(productImage)
                                                                              .build();

        Long productId = registerProductUsecase.registerProduct(registerProductCommand);

        return ResponseEntity.ok(productId);
    }
}
