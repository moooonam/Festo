package com.example.festo.order.domain;

import com.example.festo.common.jpa.MoneyConverter;
import com.example.festo.common.model.Money;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Order {

    @EmbeddedId
    private OrderNo number;

    @Embedded
    private Orderer orderer;

    @Convert(converter = MoneyConverter.class)
    private Money totalAmounts;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus orderStatus;

    private LocalDateTime orderDate;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "order_line", joinColumns = @JoinColumn(name = "order_number"))
    @OrderColumn(name = "line_idx")
    private List<OrderLine> orderLines;

    public Order(OrderNo number, Orderer orderer, List<OrderLine> orderLines) {
        this.number = number;
        this.orderer = orderer;
        this.orderLines = orderLines;
        this.orderStatus = OrderStatus.BEFORE_PAYMENT;
        this.orderDate = LocalDateTime.now();
        calculateTotalAmounts(this.orderLines);
    }

    private void calculateTotalAmounts(List<OrderLine> orderLines) {
        this.totalAmounts = new Money(orderLines.stream()
                                                .mapToInt(x -> x.getAmounts()
                                                                .getValue())
                                                .sum());
    }
}
