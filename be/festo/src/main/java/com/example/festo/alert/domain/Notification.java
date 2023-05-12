package com.example.festo.alert.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Notification {

    private final Long notificationId;

    private final String content;

    private final Long festivalId;

    private final String festivalName;

    private final Long boothId;

    private final String boothName;

    private final LocalDateTime time;

    public Notification(Long notificationId, String content, Long festivalId, String festivalName, Long boothId, String boothName, LocalDateTime time) {
        this.notificationId = notificationId;
        this.content = content;
        this.festivalId = festivalId;
        this.festivalName = festivalName;
        this.boothId = boothId;
        this.boothName = boothName;
        this.time = time;
    }
}
