package com.example.festo.booth.adapter.in.web.model;

import com.example.festo.booth.domain.BoothStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

public class BoothResponse {
    @Getter
    @Builder
    public static class Booths{
        private Long boothId;
        private String imageUrl;
        private String name;
        private String description;
    }

    @Getter
    @Builder
    public static class Detail{
        private Long boothId;
        private String name;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        private LocalTime openTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        private LocalTime closeTime;
        private String locationDescription;
        private String boothDescription;
        private String imageUrl;
        private BoothStatus status;
    }

}
