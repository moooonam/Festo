package com.example.festo.alert.application.service;

import com.example.festo.alert.adapter.in.web.model.NotificationResponse;
import com.example.festo.alert.application.port.in.LoadNotificationUseCase;
import com.example.festo.alert.application.port.in.SaveNotificationUseCase;
import com.example.festo.alert.application.port.out.LoadNotificationPort;
import com.example.festo.alert.application.port.out.SaveNotificationPort;
import com.example.festo.alert.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService implements LoadNotificationUseCase, SaveNotificationUseCase {

    private final LoadNotificationPort loadNotificationPort;

    private final SaveNotificationPort saveNotificationPort;

    @Override
    public List<NotificationResponse> loadNotificationsByReceiverId(Long receiverId) {
        List<Notification> notifications = loadNotificationPort.loadAllByReceiverId(receiverId);

        return notifications.stream().map(NotificationResponse::new).collect(Collectors.toList());
    }

    @Override
    public Notification saveNotification(Long memberId, Long orderId, long timestamp) {
        return saveNotificationPort.saveNotification(memberId, orderId, timestamp);
    }
}
