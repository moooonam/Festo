package com.example.festo.alert.adapter.in.web.model;

import com.example.festo.alert.domain.Notification;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

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
        this.festivalId = notification.getFestivalId();
        this.festivalName = notification.getFestivalName();
        this.boothId = notification.getBoothId();
        this.boothName = notification.getBoothName();
        this.time = notification.getTime();
    }
}
