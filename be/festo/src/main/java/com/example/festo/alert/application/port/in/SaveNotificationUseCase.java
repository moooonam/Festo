package com.example.festo.alert.application.port.in;

import com.example.festo.alert.domain.Notification;

public interface SaveNotificationUseCase {

    Notification saveNotification(Long memberId, Long orderId, long timestamp);
}
