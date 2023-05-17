package com.example.festo.alert.adapter.in.web.model;

import com.example.festo.alert.domain.Notification;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

@Getter
public class NotificationResponse {

    private final Long notificationId;

    private final String content;

    private final Long festivalId;

    private final String festivalName;

    private final Long boothId;

    private final String boothName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd/HH:mm", timezone = "Asia/Seoul")
    private final LocalDateTime time;

    public NotificationResponse(Notification notification) {
        this.notificationId = notification.getNotificationId();
        this.content = notification.getContent();
        this.festivalId = notification.getFestivalInfo().getFestivalId();
        this.festivalName = notification.getFestivalInfo().getFestivalName();
        this.boothId = notification.getBoothInfo().getBoothId();
        this.boothName = notification.getBoothInfo().getBoothName();
        this.time = LocalDateTime.ofInstant(Instant.ofEpochMilli(notification.getTimeStamp()), ZoneId.of("Asia/Seoul"));
    }
}
