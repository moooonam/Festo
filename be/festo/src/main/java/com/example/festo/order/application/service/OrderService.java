package com.example.festo.order.application.service;

import com.example.festo.order.adapter.in.web.model.OrderRequest;
import com.example.festo.order.application.port.out.PlaceOrderPort;
import com.example.festo.order.application.port.in.PlaceOrderUseCase;
import com.example.festo.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements PlaceOrderUseCase {

    private final PlaceOrderPort placeOrderPort;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = null;
        placeOrderPort.placeOrder(order);
    }
}
