package com.example.festo.order.adapter.out.persistence;

import com.example.festo.booth.adapter.out.persistence.BoothEntity;
import com.example.festo.booth.adapter.out.persistence.BoothRepository;
import com.example.festo.member.adapter.out.persistence.MemberRepository;
import com.example.festo.member.domain.Member;
import com.example.festo.order.application.port.out.LoadOrderPort;
import com.example.festo.order.application.port.out.PlaceOrderPort;
import com.example.festo.order.application.port.out.UpdateOrderStatusPort;
import com.example.festo.order.domain.BoothInfo;
import com.example.festo.order.domain.Order;
import com.example.festo.order.domain.OrderStatus;
import com.example.festo.order.domain.Orderer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements PlaceOrderPort, LoadOrderPort, UpdateOrderStatusPort {

    private final OrderRepository orderRepository;

    private final MemberRepository memberRepository;

    private final BoothRepository boothRepository;

    @Override
    public Long placeOrder(Order order) {
        Member orderer = memberRepository.findById(order.getOrderer()
                                                        .getMemberId())
                                         .orElseThrow(NoSuchElementException::new);
        BoothEntity boothEntity = boothRepository.findById(order.getBoothInfo()
                                                                .getBoothId())
                                                 .orElseThrow(NoSuchElementException::new);

        OrderEntity orderEntity = OrderEntity.builder()
                                             .orderer(orderer)
                                             .booth(boothEntity)
                                             .orderNo(order.getOrderNo())
                                             .orderLines(order.getOrderLines())
                                             .totalAmounts(order.getTotalAmounts())
                                             .orderStatus(order.getOrderStatus())
                                             .build();

        orderEntity = orderRepository.save(orderEntity);

        return orderEntity.getOrderId();
    }

    @Override
    public int nextOrderNo(Long boothId) {
        return orderRepository.nextOrderNo(boothId);
    }

    @Override
    public Order loadOrder(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                                                 .orElseThrow(NoSuchElementException::new);

        return mapToOrderDomain(orderEntity);
    }

    @Override
    public List<Order> loadOrdersByOrdererId(Long ordererId) {

        return orderRepository.findAllByOrdererId(ordererId)
                              .stream()
                              .map(this::mapToOrderDomain)
                              .collect(Collectors.toList());
    }

    @Override
    public List<Order> loadOrdersByBoothId(Long boothId, Long requesterId, boolean completed) {
        BoothEntity booth = boothRepository.findById(boothId)
                                           .orElseThrow(NoSuchElementException::new);

        if (!Objects.equals(booth.getOwner()
                                 .getId(), requesterId)) {
            throw new RuntimeException("권한 없음");
        }

        return orderRepository.findAllByBooth_BoothId(boothId)
                              .stream()
                              .filter(orderEntity -> {
                                  if (completed) {
                                      return orderEntity.getOrderStatus()
                                                        .equals(OrderStatus.COMPLETE);
                                  }
                                  return !orderEntity.getOrderStatus()
                                                     .equals(OrderStatus.COMPLETE);
                              })
                              .map(this::mapToOrderDomain)
                              .collect(Collectors.toList());
    }

    @Override
    public List<Order> loadOrdersByBoothId(Long boothId, boolean completed) {
        return orderRepository.findAllByBooth_BoothId(boothId)
                              .stream()
                              .map(this::mapToOrderDomain)
                              .collect(Collectors.toList());
    }

    @Override
    public Order updateOrderStatus(Order order) {
        OrderEntity orderEntity = orderRepository.findById(order.getOrderId()).orElseThrow(NoSuchElementException::new);

        orderEntity.updateStatus(order.getOrderStatus());
        orderRepository.save(orderEntity);

        return order;
    }

    private Order mapToOrderDomain(OrderEntity orderEntity) {
        Member member = orderEntity.getOrderer();
        BoothEntity booth = orderEntity.getBooth();

        Orderer orderer = new Orderer(member.getId(), member.getNickname());
        BoothInfo boothInfo = new BoothInfo(booth.getBoothId(), booth.getOwner().getId(), booth.getName());

        return Order.builder()
                    .orderId(orderEntity.getOrderId())
                    .orderNo(orderEntity.getOrderNo())
                    .boothInfo(boothInfo)
                    .orderStatus(orderEntity.getOrderStatus())
                    .orderer(orderer)
                    .totalAmounts(orderEntity.getTotalAmounts())
                    .orderTime(orderEntity.getOrderTime())
                    .orderLines(orderEntity.getOrderLines())
                    .build();
    }
}
