package com.kubo.kubo_fix.Model;

public class Message {
    private String title, message;

    public Message(){}

    public Message(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
