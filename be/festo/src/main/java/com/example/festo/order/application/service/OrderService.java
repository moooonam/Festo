package com.example.festo.order.application.service;

import com.example.festo.common.event.Events;
import com.example.festo.order.domain.OrderStatusChangedEvent;
import com.example.festo.order.adapter.in.web.model.*;
import com.example.festo.order.application.port.in.LoadOrderUseCase;
import com.example.festo.order.application.port.in.OrderStatusChangeUseCase;
import com.example.festo.order.application.port.in.PlaceOrderUseCase;
import com.example.festo.order.application.port.out.*;
import com.example.festo.order.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService implements PlaceOrderUseCase, OrderStatusChangeUseCase, LoadOrderUseCase {

    private final PlaceOrderPort placeOrderPort;

    private final LoadOrderPort loadOrderPort;

    private final UpdateOrderStatusPort updateOrderPort;

    private final LoadMenuPort loadMenuPort;

    private final LoadBoothInfoPort loadBoothInfoPort;

    private final LoadFestivalInfoPort loadFestivalInfoPort;

    private final OrdererService ordererService;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        List<OrderLine> orderLines = new ArrayList<>();
        for (OrderMenu orderMenu : orderRequest.getOrderMenus()) {
            Menu menu = loadMenuPort.loadMenu(orderMenu.getMenuId());
            orderLines.add(new OrderLine(orderMenu.getMenuId(), menu.getPrice(), orderMenu.getQuantity()));
        }

        Orderer orderer = ordererService.createOrderer(orderRequest.getOrdererMemberId());
        OrderNo orderNo = OrderNo.of(placeOrderPort.nextOrderNo(orderRequest.getBoothId()));
        BoothInfo boothInfo = loadBoothInfoPort.loadBoothInfo(orderRequest.getBoothId());

        Order order = new Order(orderNo, boothInfo, orderer, orderLines);

        Long orderId = placeOrderPort.placeOrder(order);
        Events.raise(new OrderStatusChangedEvent(orderId, order.getOrderNo()
                                                               .getNumber(), orderer.getMemberId(), order.getBoothInfo()
                                                                                                         .getOwnerId(), order.getOrderStatus()
                                                                                                                             .name()));
    }

    @Transactional
    @Override
    public void changeStatus(Long orderId, OrderStatusChangeRequest orderStatusChangeRequest) {
        Order order = loadOrderPort.loadOrder(orderId);
        order.updateStatus(orderStatusChangeRequest);

        updateOrderPort.updateOrderStatus(order);
        Events.raise(new OrderStatusChangedEvent(orderId, order.getOrderNo()
                                                               .getNumber(), order.getOrderer()
                                                                                  .getMemberId(), order.getBoothInfo()
                                                                                                       .getOwnerId(), order.getOrderStatus()
                                                                                                                           .name()));
    }

    @Override
    public OrderDetailResponse loadOrderDetail(Long orderId) {
        Order order = loadOrderPort.loadOrder(orderId);

        List<MenuResponse> menus = new ArrayList<>();
        for (OrderLine orderLine : order.getOrderLines()) {
            Menu menu = loadMenuPort.loadMenu(orderLine.getMenuId());
            menus.add(new MenuResponse(menu.getName(), orderLine.getQuantity()));
        }

        return new OrderDetailResponse(order.getOrderNo()
                                            .getNumber(), order.getOrderTime(), order.getTotalAmounts()
                                                                                     .getValue(), menus);
    }

    @Override
    public List<OrderSummaryResponse> loadOrderSummariesByOrdererId(Long ordererId) {
        List<Order> orders = loadOrderPort.loadOrdersByOrdererId(ordererId);

        List<OrderSummaryResponse> orderSummaries = new ArrayList<>();
        for (Order order : orders) {
            log.info("order Domain: {} / {}", order.getOrderId(), order.getOrderTime());
            FestivalInfo festivalInfo = loadFestivalInfoPort.loadFestivalInfoByBoothId(order.getBoothInfo()
                                                                                            .getBoothId());
            Menu firstMenu = loadMenuPort.loadMenu(order.getOrderLines()
                                                        .get(0)
                                                        .getMenuId());

            orderSummaries.add(new OrderSummaryResponse(order, festivalInfo, firstMenu, order.getOrderTime()
                                                                                             .atZone(ZoneId.of("Asia/Seoul"))
                                                                                             .toLocalDateTime()));
        }

        return orderSummaries;
    }

    @Override
    public List<OrderSummaryForBoothOwnerResponse> loadOrderSummariesByBoothId(Long boothId, Long requesterId, boolean completed) {
        List<Order> orders = loadOrderPort.loadOrdersByBoothId(boothId, requesterId, completed);

        List<OrderSummaryForBoothOwnerResponse> orderSummaries = new ArrayList<>();
        for (Order order : orders) {
            int count = order.getOrderLines()
                             .stream()
                             .mapToInt(OrderLine::getQuantity)
                             .sum() - 1;

            Menu firstMenu = loadMenuPort.loadMenu(order.getOrderLines()
                                                        .get(0)
                                                        .getMenuId());
            orderSummaries.add(new OrderSummaryForBoothOwnerResponse(order, firstMenu, count));
        }

        return orderSummaries;
    }

    @Override
    public int countWaitingByBoothId(Long boothId) {
        List<Order> orders = loadOrderPort.loadOrdersByBoothId(boothId, false);

        return (int) orders.stream()
                           .filter(order -> order.getOrderStatus()
                                                 .getNumber() <= 2)
                           .count();
    }
}
