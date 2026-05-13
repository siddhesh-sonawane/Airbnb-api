package com.Siddhesh.Airbnb.DTOs;

import java.math.BigDecimal;
import java.util.List;

public class PropertyDTO {
    private long id;
    private String title;
    private String description;
    private String address;
    private Double latitude;
    private Double longitude;
    private BigDecimal pricePerNight;
    private Integer maxGuests;
    private String ownerName;
    private long imageCount;
    private List<PropertyImageDTO> images;

    public PropertyDTO(long id, String title, String description, String address,
                       Double latitude, Double longitude, BigDecimal pricePerNight,
                       Integer maxGuests, String ownerName, long imageCount,
                       List<PropertyImageDTO> images) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pricePerNight = pricePerNight;
        this.maxGuests = maxGuests;
        this.ownerName = ownerName;
        this.imageCount = imageCount;
        this.images = images;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public BigDecimal getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(BigDecimal pricePerNight) { this.pricePerNight = pricePerNight; }
    public Integer getMaxGuests() { return maxGuests; }
    public void setMaxGuests(Integer maxGuests) { this.maxGuests = maxGuests; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public long getImageCount() { return imageCount; }
    public void setImageCount(long imageCount) { this.imageCount = imageCount; }
    public List<PropertyImageDTO> getImages() { return images; }
    public void setImages(List<PropertyImageDTO> images) { this.images = images; }
}