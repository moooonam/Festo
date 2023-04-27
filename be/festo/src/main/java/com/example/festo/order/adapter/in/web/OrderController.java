package com.example.festo.order.adapter.in.web;

import com.example.festo.order.adapter.in.web.model.OrderRequest;
import com.example.festo.order.application.port.in.PlaceOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final PlaceOrderUseCase placeOrderUseCase;

    @PostMapping("/api/v1/order")
    public ResponseEntity<Void> order(@RequestBody OrderRequest orderRequest) {
        placeOrderUseCase.placeOrder(orderRequest);

        return ResponseEntity.ok()
                             .build();
    }
}
