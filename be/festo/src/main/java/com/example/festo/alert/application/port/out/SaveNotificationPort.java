package com.example.festo.alert.application.port.out;

import com.example.festo.alert.domain.Notification;

public interface SaveNotificationPort {

    Notification saveNotification(Long memberId, Long orderId, long timestamp);
}
