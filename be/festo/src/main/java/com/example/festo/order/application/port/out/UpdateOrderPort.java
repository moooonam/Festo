package com.example.festo.order.application.port.out;

import com.example.festo.order.domain.Order;

public interface UpdateOrderPort {

    Order updateOrder(Order order);
}
