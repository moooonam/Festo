package com.example.festo.order.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoothInfo {

    private Long boothId;

    private Long ownerId;

    private String boothName;

    public boolean isOwner(Long id) {
        return this.ownerId.equals(id);
    }
}
