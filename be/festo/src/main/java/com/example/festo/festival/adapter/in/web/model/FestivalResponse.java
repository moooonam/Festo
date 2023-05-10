package com.example.festo.festival.adapter.in.web.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;


public class FestivalResponse {
    @Getter
    @Builder
    public static class MainPage {
        private Long festivalId;
        private String imageUrl;
        private String name;
    }

    @Getter
    @Builder
    public static class Search {
        private Long festivalId;
        private String imageUrl;
        private String name;
    }

    @Getter
    @Builder
    public static class Manager {
        private Long festivalId;
        private String imageUrl;
        private String name;
    }

    @Getter
    @Builder
    public static class Detail {
        private Long festivalId;
        private String imageUrl;
        private String name;
        private String address;
        private LocalDate startDate;
        private LocalDate endDate;
        private String description;
    }

    @Getter
    public static class Creation {

        private final Long festivalId;

        public Creation(Long festivalId) {
            this.festivalId = festivalId;
        }
    }

    @Getter
    public static class Invitation {

        private final Long festivalId;

        public Invitation(Long festivalId) {
            this.festivalId = festivalId;
        }
    }
}
