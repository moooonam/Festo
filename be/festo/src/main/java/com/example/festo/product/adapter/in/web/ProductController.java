package com.example.festo.product.adapter.in.web;

import com.example.festo.product.adapter.in.web.model.ProductRegisterRequest;
import com.example.festo.product.adapter.in.web.model.ProductResponse;
import com.example.festo.product.application.port.in.LoadProductUseCase;
import com.example.festo.product.application.port.in.RegisterProductCommand;
import com.example.festo.product.application.port.in.RegisterProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final RegisterProductUseCase registerProductUsecase;

    private final LoadProductUseCase loadProductUseCase;

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

    @GetMapping("/booths/{booth_id}/menus")
    public ResponseEntity<List<ProductResponse>> getMenus(@PathVariable("booth_id") Long boothId) {

        List<ProductResponse> products = loadProductUseCase.loadProductsByBoothId(boothId);

        return ResponseEntity.ok(products);
    }
}
