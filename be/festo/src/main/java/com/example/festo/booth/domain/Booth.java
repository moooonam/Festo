package com.example.festo.booth.domain;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class Booth {
    private Long boothId;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String locationDescription;
    private String boothDescription;
    private String imageUrl;
    private BoothStatus status;
    private Menu menu;
    private Owner owner;



}
