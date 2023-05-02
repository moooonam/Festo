package com.example.festo.order.domain;

import com.example.festo.common.jpa.MoneyConverter;
import com.example.festo.common.model.Money;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Embedded
    private OrderNo orderNo;

    private Long boothId;

    @Embedded
    private Orderer orderer;

    @Convert(converter = MoneyConverter.class)
    private Money totalAmounts;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus orderStatus;

    @CreatedDate
    private LocalDateTime orderDate;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "order_line", joinColumns = @JoinColumn(name = "order_number"))
    @OrderColumn(name = "line_idx")
    private List<OrderLine> orderLines;

    protected Order() {
    }

    public Order(OrderNo orderNo, Orderer orderer, List<OrderLine> orderLines) {
        this.orderNo = orderNo;
        this.orderer = orderer;
        this.orderLines = orderLines;
        this.orderStatus = OrderStatus.WAITING_ACCEPTANCE;

        calculateTotalAmounts(this.orderLines);
    }

    private void calculateTotalAmounts(List<OrderLine> orderLines) {
        this.totalAmounts = new Money(orderLines.stream()
                                                .mapToInt(x -> x.getAmounts()
                                                                .getValue())
                                                .sum());
    }
}
