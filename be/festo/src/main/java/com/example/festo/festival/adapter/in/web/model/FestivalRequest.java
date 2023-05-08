package com.example.festo.festival.adapter.in.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class FestivalRequest {
    private String festivalName;
    private String location;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private LocalDate endDate;
}
