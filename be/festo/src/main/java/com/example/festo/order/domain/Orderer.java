package com.example.festo.order.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Orderer {

    private Long memberId;

    private String name;
}
