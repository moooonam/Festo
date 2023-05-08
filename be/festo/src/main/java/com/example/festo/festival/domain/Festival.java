package com.example.festo.festival.domain;


import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class Festival {
    private Long festivalId;
    private String name;
    private String description;
    private int inviteCode;
    private String address;
    private LocalDate startDate;
    private LocalDate endDate;
    private String imageUrl;
    private Manager manager;
    private FestivalStatus festivalStatus;
    private List<BoothInfo> booths;




}
