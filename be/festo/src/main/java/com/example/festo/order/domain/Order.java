package com.example.festo.order.domain;

import com.example.festo.common.model.Money;
import com.example.festo.order.adapter.in.web.model.OrderStatusChangeRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.access.AuthorizationServiceException;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Order {

    private Long orderId;

    private OrderNo orderNo;

    private BoothInfo boothInfo;

    private Orderer orderer;

    private Money totalAmounts;

    private OrderStatus orderStatus;

    private LocalDateTime orderTime;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "order_line", joinColumns = @JoinColumn(name = "order_number"))
    @OrderColumn(name = "line_idx")
    private List<OrderLine> orderLines;

    @Builder
    public Order(Long orderId, OrderNo orderNo, BoothInfo boothInfo, Orderer orderer, Money totalAmounts, OrderStatus orderStatus, LocalDateTime orderTime, List<OrderLine> orderLines) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.boothInfo = boothInfo;
        this.orderer = orderer;
        this.totalAmounts = totalAmounts;
        this.orderStatus = orderStatus;
        this.orderTime = orderTime;
        this.orderLines = orderLines;
    }

    public Order(OrderNo orderNo, BoothInfo boothInfo, Orderer orderer, List<OrderLine> orderLines) {
        this.orderNo = orderNo;
        this.boothInfo = boothInfo;
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

    public void updateStatus(OrderStatusChangeRequest orderStatusChangeRequest) {
        if (!validateQualification(orderStatusChangeRequest.getRequesterId())) {
            throw new AuthorizationServiceException("부스 관리자만 주문 상태를 변경할 수 있습니다.");
        }

        OrderStatus status = OrderStatus.findBy(orderStatusChangeRequest.getRequestStatus());
//        validateChangeable(status);

        this.orderStatus = status;
    }

    private boolean validateChangeable(OrderStatus status) {
        if (!this.orderStatus.before(status)) {
            throw new RuntimeException("상태 변경 불가"); // TODO 정확한 예외 처리
        }

        return true;
    }

    private boolean validateQualification(Long requesterId) {
        return boothInfo.isOwner(requesterId);
    }
}
