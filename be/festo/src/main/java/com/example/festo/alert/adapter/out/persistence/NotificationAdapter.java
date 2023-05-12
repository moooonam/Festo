package com.example.festo.alert.adapter.out.persistence;

import com.example.festo.alert.application.port.out.LoadNotificationPort;
import com.example.festo.alert.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationAdapter implements LoadNotificationPort {

    private NotificationRepository notificationRepository;

    @Override
    public List<Notification> loadAllByReceiverId(Long receiverId) {

        List<NotificationEntity> notificationEntities = notificationRepository.finAllByReceiverId(receiverId);

        return null;
    }
}
