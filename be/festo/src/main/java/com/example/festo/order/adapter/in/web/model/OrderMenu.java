package com.example.festo.order.adapter.in.web.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderMenu {

    @NotNull(message = "[Request] menuId는 빈 값일 수 없습니다.")
    private Long menuId;

    @Min(value = 1, message = "[Request] quantity는 최소 1 이상이어야 합니다.")
    @NotBlank(message = "[Request] quantity는 빈 값일 수 없습니다.")
    private Integer quantity;
}
