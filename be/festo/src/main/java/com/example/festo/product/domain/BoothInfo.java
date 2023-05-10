package com.example.festo.product.domain;

import com.example.festo.booth.adapter.out.persistence.BoothEntity;
import lombok.Getter;

import java.util.Objects;

@Getter
public class BoothInfo {

    private final Long boothId;

    private final Long ownerId;

    private final String boothName;

    public BoothInfo(Long boothId, Long ownerId, String boothName) {
        this.boothId = boothId;
        this.ownerId = ownerId;
        this.boothName = boothName;
    }

    public BoothInfo(BoothEntity booth) {
        this.boothId = booth.getBoothId();
        this.ownerId = booth.getOwner()
                            .getId();
        this.boothName = booth.getName();
    }

    public boolean isOwner(Long requesterId) {
        return Objects.equals(this.ownerId, requesterId);
    }
}
