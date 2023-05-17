package com.example.festo.booth.adapter.in.web.model;

import lombok.Builder;
import lombok.Getter;

public class FiestaResponse {
    @Getter
    @Builder
    public static class Owner{
        private Long festivalId;
        private String imageUrl;
        private String name;
    }
}
