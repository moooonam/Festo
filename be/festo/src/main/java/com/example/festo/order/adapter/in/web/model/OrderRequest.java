package com.example.festo.order.adapter.in.web.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {

    private List<OrderProduct> orderProducts;

    private Long ordererMemberId;

}
