package com.example.festo.order.adapter.out.persistence;

import com.example.festo.order.application.port.out.LoadOrderPort;
import com.example.festo.order.application.port.out.PlaceOrderPort;
import com.example.festo.order.application.port.out.UpdateOrderPort;
import com.example.festo.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements PlaceOrderPort, LoadOrderPort, UpdateOrderPort {

    private final OrderRepository orderRepository;

    @Override
    public void placeOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public int nextOrderNo(Long boothId) {
        return orderRepository.nextOrderNo(boothId);
    }

    @Override
    public Order loadOrder(Long orderId) {
        return orderRepository.findById(orderId)
                              .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }
}
