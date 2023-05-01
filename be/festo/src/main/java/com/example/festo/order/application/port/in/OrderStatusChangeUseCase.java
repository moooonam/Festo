package com.example.festo.order.application.port.in;

public interface OrderStatusChangeUseCase {
    void changeStatus(String orderNo, OrderStatusChangeUseCase orderStatusChangeUseCase);
}
