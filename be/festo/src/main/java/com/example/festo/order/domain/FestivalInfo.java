package com.example.festo.order.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FestivalInfo {

    private Long festivalId;

    private String festivalName;

    private String imageUrl;
}
