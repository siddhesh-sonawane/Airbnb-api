package com.Siddhesh.Airbnb.Controller;

import com.Siddhesh.Airbnb.DTOs.PropertyDTO;
import com.Siddhesh.Airbnb.Model.Property;
import com.Siddhesh.Airbnb.Services.PropertyService;
import com.Siddhesh.Airbnb.Utility.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/property")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PropertyDTO>>> getAllProperties() {

        List<PropertyDTO> properties = propertyService.getAllProperties();

        return ResponseEntity.ok(
                ApiResponse.success("Properties fetched", properties)
        );
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Property>> createProperty(
            @RequestParam Long ownerId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String address,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude,
            @RequestParam BigDecimal pricePerNight,
            @RequestParam Integer maxGuests,
            @RequestParam(required = false, defaultValue = "0") BigDecimal cleaningFee,
            @RequestParam(required = false, defaultValue = "0") BigDecimal serviceFee,
            @RequestParam(required = false, defaultValue = "FLEXIBLE") Property.CancellationPolicy cancellationPolicy,
            @RequestParam(required = false) List<MultipartFile> images
    ) {
        try {
            Property result = propertyService.insertProperty(
                    ownerId, title, description, address,
                    latitude, longitude, pricePerNight, maxGuests,
                    cleaningFee, serviceFee, cancellationPolicy, images
            );
            return ResponseEntity.status(201)
                    .body(ApiResponse.success("Property created", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), "PROPERTY_CREATE_FAILED"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Property>> fetchByPropertyId(@PathVariable long id) {

        Property property = propertyService.fetchPropertyById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Property fetched", property)
        );
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<ApiResponse<List<PropertyDTO>>> getPropertiesByOwnerId(@PathVariable Long ownerId) {

        List<PropertyDTO> properties = propertyService.getPropertiesByOwnerId(ownerId);

        return ResponseEntity.ok(
                ApiResponse.success("Owner properties fetched", properties)
        );
    }

    @GetMapping("/propertyWithImages")
    public ResponseEntity<ApiResponse<List<PropertyDTO>>> getAllPropertiesWithImages() {

        List<PropertyDTO> properties = propertyService.getAllPropertiesWithImages();

        return ResponseEntity.ok(
                ApiResponse.success("Properties with images fetched", properties)
        );
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<PropertyDTO>>> getAllPropertiesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<PropertyDTO> properties = propertyService.getAllPropertiesPaginated(pageable);

        return ResponseEntity.ok(
                ApiResponse.success("Properties fetched paginated", properties)
        );
    }
}