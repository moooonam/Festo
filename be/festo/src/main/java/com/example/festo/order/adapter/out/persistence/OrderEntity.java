package com.example.festo.order.adapter.out.persistence;

import com.example.festo.booth.adapter.out.persistence.BoothEntity;
import com.example.festo.common.jpa.MoneyConverter;
import com.example.festo.common.model.Money;
import com.example.festo.member.domain.Member;
import com.example.festo.order.domain.OrderLine;
import com.example.festo.order.domain.OrderNo;
import com.example.festo.order.domain.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Embedded
    private OrderNo orderNo;

    @ManyToOne
    @JoinColumn(name = "booth_id", nullable = false)
    private BoothEntity booth;

    @ManyToOne
    @JoinColumn(name = "orderer_id")
    private Member orderer;

    @Column(nullable = false)
    @Convert(converter = MoneyConverter.class)
    private Money totalAmounts;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus orderStatus;

    @CreatedDate
    private LocalDateTime orderTime;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "order_line", joinColumns = @JoinColumn(name = "order_number"))
    @OrderColumn(name = "line_idx", nullable = false)
    private List<OrderLine> orderLines;

    @Builder
    public OrderEntity(BoothEntity booth, OrderNo orderNo, Member orderer, List<OrderLine> orderLines, Money totalAmounts, OrderStatus orderStatus) {
        this.booth = booth;
        this.orderNo = orderNo;
        this.orderer = orderer;
        this.orderLines = orderLines;
        this.totalAmounts = totalAmounts;
        this.orderStatus = orderStatus;
    }

    @PrePersist
    public void onPrePersist(){
        this.orderTime = LocalDateTime.now();
    }

    public void updateStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
