package com.example.dose.Models;

public class MessageModel {
    String message,userId,time;

    public MessageModel(String message, String userId, String time) {
        this.message = message;
        this.userId = userId;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
