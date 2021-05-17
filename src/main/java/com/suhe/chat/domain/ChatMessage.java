package com.suhe.chat.domain;

import java.time.LocalTime;

public class ChatMessage {
    private String from;
    private String message;
    private LocalTime time;

    public ChatMessage(String from, String message) {
        this.from = from;
        this.message = message;
        this.time = LocalTime.now();
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public LocalTime getTime() {
        return time;
    }
}
