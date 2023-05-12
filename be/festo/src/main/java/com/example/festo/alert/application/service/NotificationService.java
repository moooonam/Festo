package com.example.festo.alert.application.service;

import com.example.festo.alert.adapter.in.web.model.NotificationResponse;
import com.example.festo.alert.application.port.in.LoadNotificationUseCase;
import com.example.festo.alert.application.port.out.LoadNotificationPort;
import com.example.festo.alert.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService implements LoadNotificationUseCase {

    private final LoadNotificationPort loadNotificationPort;

    @Override
    public List<NotificationResponse> loadNotificationsByReceiverId(Long receiverId) {
        List<Notification> notifications = loadNotificationPort.loadAllByReceiverId(receiverId);

        return notifications.stream().map(NotificationResponse::new).collect(Collectors.toList());
   }
}
