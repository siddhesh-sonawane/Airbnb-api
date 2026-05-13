package com.Siddhesh.Airbnb.DTOs;

import java.time.LocalDateTime;

public class ReviewDTO {
    private long id;
    private String reviewerName;
    private String revieweeName;
    private String propertyTitle;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;

    public ReviewDTO(long id, String reviewerName, String revieweeName,
                     String propertyTitle, int rating, String comment,
                     LocalDateTime createdAt) {
        this.id = id;
        this.reviewerName = reviewerName;
        this.revieweeName = revieweeName;
        this.propertyTitle = propertyTitle;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getReviewerName() { return reviewerName; }
    public void setReviewerName(String reviewerName) { this.reviewerName = reviewerName; }
    public String getRevieweeName() { return revieweeName; }
    public void setRevieweeName(String revieweeName) { this.revieweeName = revieweeName; }
    public String getPropertyTitle() { return propertyTitle; }
    public void setPropertyTitle(String propertyTitle) { this.propertyTitle = propertyTitle; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}