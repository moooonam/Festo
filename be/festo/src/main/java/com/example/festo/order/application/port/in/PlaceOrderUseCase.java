package com.example.festo.order.application.port.in;

import com.example.festo.order.adapter.in.web.model.OrderRequest;

public interface PlaceOrderUseCase {

    void placeOrder(OrderRequest orderRequest);
}
