package com.example.festo.alert.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Notification {

    private final Long notificationId;

    private final Long receiverId;

    private final String content;

    private final FestivalInfo festivalInfo;

    private final BoothInfo boothInfo;

    private final long timeStamp;

    @Builder
    public Notification(Long notificationId, Long receiverId, String content, FestivalInfo festivalInfo, BoothInfo boothInfo, long timeStamp) {
        this.notificationId = notificationId;
        this.receiverId = receiverId;
        this.content = content;
        this.festivalInfo = festivalInfo;
        this.boothInfo = boothInfo;
        this.timeStamp = timeStamp;
    }
}
