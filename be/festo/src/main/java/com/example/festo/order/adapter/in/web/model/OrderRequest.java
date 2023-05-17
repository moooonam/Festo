package com.example.festo.order.adapter.in.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {

    @NotNull(message = "[Request] orderMenus는 빈 값일 수 없습니다.")
    private List<OrderMenu> orderMenus;

    private Long ordererMemberId;

    @NotNull(message = "[Request] boothId는 빈 값일 수 없습니다.")
    private Long boothId;

    // TODO paymentInnfo로 유효성 검사 시 주석 제거할 것
    // @NotBlank(message = "[Request] paymentInfo는 빈 값일 수 없습니다.")
    private PaymentInfo paymentInfo;

}
