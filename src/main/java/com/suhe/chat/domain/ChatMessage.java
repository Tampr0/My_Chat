package com.suhe.chat.domain;

import java.util.Random;

public class ChatMessage {
    private String from;
    private String message;
    private int random;

    public ChatMessage(String from, String message) {
        this.from = from;
        this.message = message;

        Random r = new Random();
        random = r.nextInt(10);
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public int getRandom() {
        return random;
    }
}
