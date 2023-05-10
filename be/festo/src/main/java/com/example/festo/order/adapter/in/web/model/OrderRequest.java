package com.example.festo.order.adapter.in.web.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {

    private List<OrderMenu> orderMenus;

    private Long ordererMemberId;

    private Long boothId;

    private PaymentInfo paymentInfo;

}
