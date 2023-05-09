package com.example.festo.festival.application.port.out;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class SaveFestivalCommand {
    private String festivalName;
    private String location;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String imageUrl;


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
