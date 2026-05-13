package com.Siddhesh.Airbnb.DTOs;

public class PropertyAmenityRequestDTO {
    private Long propertyId;
    private Long amenityId;

    public Long getPropertyId() { return propertyId; }
    public void setPropertyId(Long propertyId) { this.propertyId = propertyId; }
    public Long getAmenityId() { return amenityId; }
    public void setAmenityId(Long amenityId) { this.amenityId = amenityId; }
}