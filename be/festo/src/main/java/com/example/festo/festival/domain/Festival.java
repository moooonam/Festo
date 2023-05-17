package com.example.festo.festival.domain;


import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class Festival {
    private Long festivalId;
    private String name;
    private String description;
    private String inviteCode;
    private String address;
    private LocalDate startDate;
    private LocalDate endDate;
    private String imageUrl;
    private Manager manager;
    private FestivalStatus festivalStatus;
    private List<BoothInfo> booths;

    public Festival(Long festivalId, String name, String description, String inviteCode, String address, LocalDate startDate, LocalDate endDate, String imageUrl) {
        this.festivalId = festivalId;
        this.name = name;
        this.description = description;
        this.inviteCode = inviteCode;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.imageUrl = imageUrl;
    }
}
