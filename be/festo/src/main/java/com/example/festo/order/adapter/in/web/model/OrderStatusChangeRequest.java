package com.example.festo.order.adapter.in.web.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderStatusChangeRequest {

    private Long requesterId;

    private String requestStatus;
}
