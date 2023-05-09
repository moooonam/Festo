package com.example.festo.order.application.port.out;

import com.example.festo.order.domain.Order;

public interface PlaceOrderPort {

    void placeOrder(Order order);

    int nextOrderNo(Long boothId);
}
