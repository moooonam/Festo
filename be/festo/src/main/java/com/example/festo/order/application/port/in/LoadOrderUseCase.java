package com.example.festo.order.application.port.in;

import com.example.festo.order.adapter.in.web.model.OrderDetail;
import com.example.festo.order.adapter.in.web.model.OrderSummary;

import java.util.List;

public interface LoadOrderUseCase {

    OrderDetail loadOrderDetail(Long orderId);

    List<OrderSummary> loadOrderSummariesByOrdererId(Long ordererId);
}
