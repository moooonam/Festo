package com.example.festo.order.adapter.in.web;

import com.example.festo.order.adapter.in.web.model.*;
import com.example.festo.order.application.port.in.LoadOrderUseCase;
import com.example.festo.order.application.port.in.OrderStatusChangeUseCase;
import com.example.festo.order.application.port.in.PlaceOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final PlaceOrderUseCase placeOrderUseCase;

    private final LoadOrderUseCase loadOrderUseCase;

    private final OrderStatusChangeUseCase orderStatusChangeUseCase;

    @PostMapping("/orders")
    public ResponseEntity<Void> order(@RequestBody OrderRequest orderRequest) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                                                              .getAuthentication()
                                                              .getPrincipal();

        orderRequest.setOrdererMemberId(Long.parseLong(user.getUsername()));

        placeOrderUseCase.placeOrder(orderRequest);

        return ResponseEntity.ok()
                             .build();
    }

    @PatchMapping("/orders/{orderId}/status")
    public ResponseEntity<Void> updateState(@PathVariable("orderId") Long orderId, OrderStatusChangeRequest orderStatusChangeRequest) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                                                              .getAuthentication()
                                                              .getPrincipal();

        orderStatusChangeRequest.setRequesterId(Long.parseLong(user.getUsername()));

        orderStatusChangeUseCase.changeStatus(orderId, orderStatusChangeRequest);

        return ResponseEntity.ok()
                             .build();
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderDetailResponse> getOrderDetail(@PathVariable("orderId") Long orderId) {
        OrderDetailResponse orderDetailResponse = loadOrderUseCase.loadOrderDetail(orderId);

        return ResponseEntity.ok(orderDetailResponse);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderSummaryResponse>> getOrders() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                                                              .getAuthentication()
                                                              .getPrincipal();

        List<OrderSummaryResponse> orders = loadOrderUseCase.loadOrderSummariesByOrdererId(Long.parseLong(user.getUsername()));

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/booths/{boothId}/orders")
    public ResponseEntity<List<OrderSummaryForBoothOwnerResponse>> getOrdersByBooth(@PathVariable Long boothId, @RequestParam(name = "completed") boolean completed) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                                                              .getAuthentication()
                                                              .getPrincipal();

        List<OrderSummaryForBoothOwnerResponse> orders = loadOrderUseCase.loadOrderSummariesByBoothId(boothId, Long.parseLong(user.getUsername()), completed);

        return ResponseEntity.ok(orders);
    }
}
