package com.example.festo.order.application.port.out;

import com.example.festo.order.adapter.in.web.model.OrderSummary;
import com.example.festo.order.domain.Order;

import java.util.List;

public interface LoadOrderPort {

    Order loadOrder(Long orderId);

    List<Order> loadOrdersByOrdererId(Long ordererId);
}
