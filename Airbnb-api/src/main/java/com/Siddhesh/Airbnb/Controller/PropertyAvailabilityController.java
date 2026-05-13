package com.Siddhesh.Airbnb.Controller;

import com.Siddhesh.Airbnb.Model.PropertyAvailability;
import com.Siddhesh.Airbnb.Services.PropertyAvailabilityService;
import com.Siddhesh.Airbnb.Utility.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/availability")
public class PropertyAvailabilityController {

    private final PropertyAvailabilityService propertyAvailabilityService;

    public PropertyAvailabilityController(PropertyAvailabilityService propertyAvailabilityService) {
        this.propertyAvailabilityService = propertyAvailabilityService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PropertyAvailability>>> fetchAllAvailability() {

        List<PropertyAvailability> availabilities = propertyAvailabilityService.fetchAllAvailability();

        return ResponseEntity.ok(
                ApiResponse.success("Availabilities fetched", availabilities)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PropertyAvailability>> createAvailability(
            @RequestBody PropertyAvailability availability) {

        PropertyAvailability result = propertyAvailabilityService.insertAvailability(availability);

        return ResponseEntity.status(201)
                .body(ApiResponse.success("Availability created", result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PropertyAvailability>> fetchByAvailabilityId(@PathVariable long id) {

        PropertyAvailability availability = propertyAvailabilityService.fetchAvailabilityById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Availability fetched", availability)
        );
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<ApiResponse<List<PropertyAvailability>>> getAvailabilityByPropertyId(
            @PathVariable Long propertyId) {

        List<PropertyAvailability> availabilities =
                propertyAvailabilityService.getAvailabilityByPropertyId(propertyId);

        return ResponseEntity.ok(
                ApiResponse.success("Property availability fetched", availabilities)
        );
    }
}