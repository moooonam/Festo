package com.example.festo.order.adapter.in.web;

import com.example.festo.order.adapter.in.web.model.OrderRequest;
import com.example.festo.order.adapter.in.web.model.OrderStatusChangeRequest;
import com.example.festo.order.application.port.in.OrderStatusChangeUseCase;
import com.example.festo.order.application.port.in.PlaceOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final PlaceOrderUseCase placeOrderUseCase;

    private final OrderStatusChangeUseCase orderStatusChangeUseCase;

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

    @PatchMapping("/api/v1/orders/{orderNo}")
    public ResponseEntity<Void> updateState(@PathVariable("orderNo") String orderNo, OrderStatusChangeRequest orderStatusChangeRequest) {
        User user = (User) SecurityContextHolder.getContext()
                                                .getAuthentication()
                                                .getPrincipal();

        orderStatusChangeUseCase.changeStatus(orderNo, orderStatusChangeUseCase);

        return ResponseEntity.ok()
                             .build();
    }
}
