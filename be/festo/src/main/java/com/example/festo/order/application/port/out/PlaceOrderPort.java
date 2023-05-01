package com.example.festo.order.application.port.out;

import com.example.festo.order.domain.Order;
import com.example.festo.order.domain.OrderNo;

public interface PlaceOrderPort {

    void placeOrder(Order order);

    OrderNo nextOrderNo();
}
