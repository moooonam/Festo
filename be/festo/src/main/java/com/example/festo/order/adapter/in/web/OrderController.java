package com.example.festo.order.adapter.in.web;

import com.example.festo.order.adapter.in.web.model.OrderRequest;
import com.example.festo.order.application.port.in.PlaceOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final PlaceOrderUseCase placeOrderUseCase;

    @PostMapping("/api/v1/orders")
    public ResponseEntity<Void> order(@RequestBody OrderRequest orderRequest) {
        User user = (User) SecurityContextHolder.getContext()
                                                .getAuthentication()
                                                .getPrincipal();

        orderRequest.setOrdererMemberId(Long.parseLong(user.getUsername()));

        placeOrderUseCase.placeOrder(orderRequest);

        return ResponseEntity.ok()
                             .build();
    }
}
