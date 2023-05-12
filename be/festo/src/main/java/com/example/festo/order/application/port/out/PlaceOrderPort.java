package com.example.festo.order.application.port.out;

import com.example.festo.order.domain.Order;

public interface PlaceOrderPort {

    Long placeOrder(Order order);

    int nextOrderNo(Long boothId);
}
