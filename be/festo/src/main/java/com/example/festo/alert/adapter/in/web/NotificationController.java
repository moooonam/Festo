package com.example.festo.alert.adapter.in.web;

import com.example.festo.alert.adapter.in.web.model.NotificationResponse;
import com.example.festo.alert.application.port.in.LoadNotificationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final LoadNotificationUseCase loadNotificationUseCase;

    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationResponse>> getNotifications() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                                                              .getAuthentication()
                                                              .getPrincipal();

        List<NotificationResponse> notifications = loadNotificationUseCase.loadNotificationsByReceiverId(Long.parseLong(user.getUsername()));
        return ResponseEntity.ok(notifications);
    }
}
