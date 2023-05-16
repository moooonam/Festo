package com.example.festo.alert.domain;

import lombok.Getter;

@Getter
public class BoothInfo {

    private final Long boothId;

    private final String boothName;

    public BoothInfo(Long boothId, String boothName) {
        this.boothId = boothId;
        this.boothName = boothName;
    }
}
