package com.example.festo.order.adapter.out.persistence;

import com.example.festo.order.application.port.out.PlaceOrderPort;
import com.example.festo.order.domain.Order;
import com.example.festo.order.domain.OrderNo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements PlaceOrderPort {

    private final OrderRepository orderRepository;

    @Override
    public void placeOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public OrderNo nextOrderNo() {
        return orderRepository.nextOrderNo();
    }
}
