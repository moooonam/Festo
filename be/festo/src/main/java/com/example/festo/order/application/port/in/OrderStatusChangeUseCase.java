package com.example.festo.order.application.port.in;

import com.example.festo.order.adapter.in.web.model.OrderStatusChangeRequest;

public interface OrderStatusChangeUseCase {
    void changeStatus(Long orderId, OrderStatusChangeRequest orderStatusChangeUseCase);
}
