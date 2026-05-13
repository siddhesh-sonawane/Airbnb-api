package com.Siddhesh.Airbnb.DTOs;

import java.util.List;

public class PropertyAmenityDTO {
    private long propertyId;
    private String propertyTitle;
    private String propertyAddress;
    private long amenityCount;
    private List<AmenityDTO> amenities;

    public PropertyAmenityDTO(long propertyId, String propertyTitle,
                              String propertyAddress, long amenityCount,
                              List<AmenityDTO> amenities) {
        this.propertyId = propertyId;
        this.propertyTitle = propertyTitle;
        this.propertyAddress = propertyAddress;
        this.amenityCount = amenityCount;
        this.amenities = amenities;
    }

    public long getPropertyId() { return propertyId; }
    public void setPropertyId(long propertyId) { this.propertyId = propertyId; }
    public String getPropertyTitle() { return propertyTitle; }
    public void setPropertyTitle(String propertyTitle) { this.propertyTitle = propertyTitle; }
    public String getPropertyAddress() { return propertyAddress; }
    public void setPropertyAddress(String propertyAddress) { this.propertyAddress = propertyAddress; }
    public long getAmenityCount() { return amenityCount; }
    public void setAmenityCount(long amenityCount) { this.amenityCount = amenityCount; }
    public List<AmenityDTO> getAmenities() { return amenities; }
    public void setAmenities(List<AmenityDTO> amenities) { this.amenities = amenities; }
}