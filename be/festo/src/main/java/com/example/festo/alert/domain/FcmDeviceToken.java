package com.example.festo.alert.domain;

import lombok.Getter;

@Getter
public class FcmDeviceToken {

    private final Long id;

    private final Long memberId;

    private final String token;

    public FcmDeviceToken(Long id, Long memberId, String token) {
        this.id = id;
        this.memberId = memberId;
        this.token = token;
    }
}
