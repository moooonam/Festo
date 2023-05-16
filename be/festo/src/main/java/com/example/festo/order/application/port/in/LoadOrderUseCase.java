package com.example.festo.order.application.port.in;

import com.example.festo.order.adapter.in.web.model.OrderDetailResponse;
import com.example.festo.order.adapter.in.web.model.OrderSummaryForBoothOwnerResponse;
import com.example.festo.order.adapter.in.web.model.OrderSummaryResponse;

import java.util.List;

public interface LoadOrderUseCase {

    OrderDetailResponse loadOrderDetail(Long orderId);

    List<OrderSummaryResponse> loadOrderSummariesByOrdererId(Long ordererId);

    List<OrderSummaryForBoothOwnerResponse> loadOrderSummariesByBoothId(Long boothId, Long requesterId, boolean completed);

    int countWaitingByBoothId(Long boothId);
}
