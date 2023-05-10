package com.example.festo.booth.adapter.in.web.model;

import lombok.Getter;

@Getter
public class BoothCreationResponse {

    private final Long boothId;

    public BoothCreationResponse(Long boothId) {
        this.boothId = boothId;
    }
}
