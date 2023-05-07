package com.example.festo.order.application.port.in;

import com.example.festo.order.adapter.in.web.model.OrderDetail;

public interface LoadOrderUseCase {

    OrderDetail loadOrderDetail(Long orderId);
}
