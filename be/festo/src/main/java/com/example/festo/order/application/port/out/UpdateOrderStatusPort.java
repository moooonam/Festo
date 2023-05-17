package com.example.festo.order.application.port.out;

import com.example.festo.order.domain.Order;

public interface UpdateOrderStatusPort {

    Order updateOrderStatus(Order order);
}
