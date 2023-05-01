package com.example.festo.order.application.service;

import com.example.festo.order.adapter.in.web.model.OrderProduct;
import com.example.festo.order.adapter.in.web.model.OrderRequest;
import com.example.festo.order.adapter.out.persistence.ProductRepository;
import com.example.festo.order.application.port.in.PlaceOrderUseCase;
import com.example.festo.order.application.port.out.PlaceOrderPort;
import com.example.festo.order.domain.Order;
import com.example.festo.order.domain.OrderLine;
import com.example.festo.order.domain.OrderNo;
import com.example.festo.order.domain.Orderer;
import com.example.festo.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderService implements PlaceOrderUseCase {

    private final PlaceOrderPort placeOrderPort;

    private final ProductRepository productRepository;

    private final OrdererService ordererService;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        List<OrderLine> orderLines = new ArrayList<>();
        for (OrderProduct orderProduct : orderRequest.getOrderProducts()) {
            Product product = productRepository.findById(orderProduct.getProductId())
                                               .orElseThrow(NoSuchElementException::new);
            orderLines.add(new OrderLine(orderProduct.getProductId(), product.getPrice(), orderProduct.getQuantity()));
        }

        Orderer orderer = ordererService.createOrderer(orderRequest.getOrdererMemberId());
        OrderNo orderNo = placeOrderPort.nextOrderNo();
        Order order = new Order(orderNo, orderer, orderLines);

        placeOrderPort.placeOrder(order);
    }
}
