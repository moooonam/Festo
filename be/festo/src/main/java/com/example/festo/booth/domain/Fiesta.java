package com.example.festo.booth.domain;

import lombok.Getter;

@Getter
public class Fiesta {
    private Long fiestaId;
    private String fiestaName;
    private String fiestaImgUrl;

    public Fiesta(Long fiestaId, String fiestaName, String fiestaImgUrl) {
        this.fiestaId = fiestaId;
        this.fiestaName = fiestaName;
        this.fiestaImgUrl = fiestaImgUrl;
    }
}
