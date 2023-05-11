package com.example.festo.common.event;

import lombok.Getter;

@Getter
public class FcmEvent extends Event {

    private final String title;

    private final String content;

    public FcmEvent(String title, String content) {
        super();
        this.title = title;
        this.content = content;
    }
}
