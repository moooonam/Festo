package com.example.festo.alert.domain;

import lombok.Getter;

@Getter
public class FestivalInfo {

    private final Long festivalId;

    private final String festivalName;

    public FestivalInfo(Long festivalId, String festivalName) {
        this.festivalId = festivalId;
        this.festivalName = festivalName;
    }
}
