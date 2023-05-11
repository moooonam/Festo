package com.example.festo.common.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class FcmEventHandler {

    @Async
    @EventListener(Event.class)
    public void sendFcm(Event event) {
        // TODO fcm에 메시지 던지기
        FcmEvent fcmevent = (FcmEvent) event;
        System.out.println("메시지 전송" + fcmevent.getTitle() + " " + fcmevent.getContent());
    }
}
