package com.example.festo.order.adapter.in.web.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderMenu {

    @NotBlank(message = "[Request] menuId는 빈 값일 수 없습니다.")
    private Long menuId;

    @NotBlank(message = "[Request] quantity는 빈 값일 수 없습니다.")
    private Integer quantity;
}
