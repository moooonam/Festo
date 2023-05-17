package com.example.festo.alert.application.port.out;

import com.example.festo.alert.domain.Notification;

import java.util.List;

public interface LoadNotificationPort {

    List<Notification> loadAllByReceiverId(Long receiverId);
}
