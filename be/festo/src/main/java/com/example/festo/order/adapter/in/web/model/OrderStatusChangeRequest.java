package com.example.festo.order.adapter.in.web.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderStatusChangeRequest {

    private Long requesterId;

    @NotBlank(message = "[Request] requestStatus는 빈 값일 수 없습니다.")
    private String requestStatus;
}
