package com.Siddhesh.Airbnb.DTOs;

import com.Siddhesh.Airbnb.Model.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentDTO {
    private long id;
    private String guestName;
    private String propertyTitle;
    private BigDecimal amount;
    private Payment.Status status;
    private String paymentMethod;
    private String transactionId;
    private LocalDateTime createdAt;

    public PaymentDTO(long id, String guestName, String propertyTitle,
                      BigDecimal amount, Payment.Status status,
                      String paymentMethod, String transactionId,
                      LocalDateTime createdAt) {
        this.id = id;
        this.guestName = guestName;
        this.propertyTitle = propertyTitle;
        this.amount = amount;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.createdAt = createdAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }
    public String getPropertyTitle() { return propertyTitle; }
    public void setPropertyTitle(String propertyTitle) { this.propertyTitle = propertyTitle; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public Payment.Status getStatus() { return status; }
    public void setStatus(Payment.Status status) { this.status = status; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}