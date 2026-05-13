package com.Siddhesh.Airbnb.DTOs;

import java.time.LocalDateTime;
import java.util.List;

public class ConversationDTO {
    private long id;
    private String propertyTitle;
    private String guestName;
    private String ownerName;
    private List<MessageDTO> messages;
    private LocalDateTime createdAt;

    public ConversationDTO(long id, String propertyTitle, String guestName,
                           String ownerName, List<MessageDTO> messages,
                           LocalDateTime createdAt) {
        this.id = id;
        this.propertyTitle = propertyTitle;
        this.guestName = guestName;
        this.ownerName = ownerName;
        this.messages = messages;
        this.createdAt = createdAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getPropertyTitle() { return propertyTitle; }
    public void setPropertyTitle(String propertyTitle) { this.propertyTitle = propertyTitle; }
    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public List<MessageDTO> getMessages() { return messages; }
    public void setMessages(List<MessageDTO> messages) { this.messages = messages; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}