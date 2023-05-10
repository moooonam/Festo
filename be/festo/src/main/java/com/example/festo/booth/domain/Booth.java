package com.example.festo.booth.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class Booth {
    private Long boothId;
    private String name;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String locationDescription;
    private String boothDescription;
    private String imageUrl;
    private BoothStatus status;
    private Menu menu;
    private Owner owner;

    @Builder
    public Booth(Long boothId, String name, LocalTime openTime, LocalTime closeTime, String locationDescription, String boothDescription, String imageUrl, BoothStatus status) {
        this.boothId = boothId;
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.locationDescription = locationDescription;
        this.boothDescription = boothDescription;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    @Builder
    public Booth(Long boothId, String name, String boothDescription, String imageUrl) {
        this.boothId = boothId;
        this.name = name;
        this.boothDescription = boothDescription;
        this.imageUrl = imageUrl;
    }
}
