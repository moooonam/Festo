package com.example.festo.festival.adapter.in.web.model;

import lombok.Builder;
import lombok.Getter;


public class FestivalResponse {
    @Getter
    @Builder
    public static class MainPage{
        private Long festivalId;
        private String imageUrl;
        private String name;
    }
    @Getter
    @Builder
    public static class Search{
        private Long festivalId;
        private String imageUrl;
        private String name;
    }
}
