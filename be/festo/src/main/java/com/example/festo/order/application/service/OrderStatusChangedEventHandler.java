package com.example.festo.order.application.service;

import com.example.festo.order.domain.OrderStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderStatusChangedEventHandler {

    @Async
    @EventListener(OrderStatusChangedEvent.class)
    public void handle(OrderStatusChangedEvent event) throws InterruptedException {
        Thread.sleep(2000);

        // TODO fcm으로 메시지 보내기
        // TODO 알림 메시지 db에 저장하기

        String status = event.getOrderStatus();

        if (status.equals("WAITING_ACCEPTANCE")) { // 부스 관리자에게 보낼 메시지
            log.info("ORDER STATUS CHANGED EVENT: 받는 사람 - {}, 상태 - {}", event.getBoothOwnerId(), event.getOrderStatus());
        } else if (status.equals("PREPARING_ORDER") || status.equals("WAITING_RECEIVE")) { // 주문자에게 보낼 메시지
            log.info("ORDER STATUS CHANGED EVENT: 받는 사람 - {}, 상태 - {}", event.getOrdererId(), event.getOrderStatus());
        } else { // 부스 관리자와 주문자 모두에게 보낼 메시지
            log.info("ORDER STATUS CHANGED EVENT: 받는 사람 - {}, 상태 - {}", event.getOrdererId() + " " + event.getBoothOwnerId(), event.getOrderStatus());
        }

    }
}
