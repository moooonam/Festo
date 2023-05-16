package com.example.festo.alert.application.port.in;

import com.example.festo.alert.adapter.in.web.model.NotificationResponse;

import java.util.List;

public interface LoadNotificationUseCase {

    List<NotificationResponse> loadNotificationsByReceiverId(Long receiverId);
}
