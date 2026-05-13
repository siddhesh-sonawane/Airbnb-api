package com.Siddhesh.Airbnb.DTOs;

import java.time.LocalDateTime;

public class MessageDTO {
    private String senderName;
    private String message;
    private LocalDateTime timestamp;

    public MessageDTO(String senderName, String message, LocalDateTime timestamp) {
        this.senderName = senderName;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}