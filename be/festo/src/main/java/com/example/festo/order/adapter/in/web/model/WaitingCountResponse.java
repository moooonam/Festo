package com.example.festo.order.adapter.in.web.model;

import lombok.Getter;

@Getter
public class WaitingCountResponse {

    private final int waiting;

    public WaitingCountResponse(int waiting) {
        this.waiting = waiting;
    }
}
