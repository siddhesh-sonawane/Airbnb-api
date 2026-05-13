package com.Siddhesh.Airbnb.Controller;

import com.Siddhesh.Airbnb.DTOs.PropertyAmenityDTO;
import com.Siddhesh.Airbnb.DTOs.PropertyAmenityRequestDTO;
import com.Siddhesh.Airbnb.Model.PropertyAmenity;
import com.Siddhesh.Airbnb.Services.PropertyAmenityService;
import com.Siddhesh.Airbnb.Utility.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/property-amenity")
public class PropertyAmenityController {

    private final PropertyAmenityService propertyAmenityService;

    public PropertyAmenityController(PropertyAmenityService propertyAmenityService) {
        this.propertyAmenityService = propertyAmenityService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PropertyAmenityDTO>>> getAllPropertyAmenities() {

        List<PropertyAmenityDTO> propertyAmenities =
                propertyAmenityService.getAllPropertyAmenities();

        return ResponseEntity.ok(
                ApiResponse.success("Property amenities fetched", propertyAmenities)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PropertyAmenity>> createPropertyAmenity(
            @RequestBody PropertyAmenityRequestDTO request
    ) {
        try {
            PropertyAmenity result = propertyAmenityService.insertPropertyAmenity(
                    request.getPropertyId(),
                    request.getAmenityId()
            );
            return ResponseEntity.status(201)
                    .body(ApiResponse.success("Property amenity created", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), "PROPERTY_AMENITY_CREATE_FAILED"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PropertyAmenity>> fetchByPropertyAmenityId(
            @PathVariable long id) {

        PropertyAmenity propertyAmenity =
                propertyAmenityService.fetchPropertyAmenityById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Property amenity fetched", propertyAmenity)
        );
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<ApiResponse<List<PropertyAmenityDTO>>> getAmenitiesByPropertyId(
            @PathVariable Long propertyId) {

        List<PropertyAmenityDTO> amenities =
                propertyAmenityService.getAmenitiesByPropertyId(propertyId);

        return ResponseEntity.ok(
                ApiResponse.success("Property amenities fetched", amenities)
        );
    }
}