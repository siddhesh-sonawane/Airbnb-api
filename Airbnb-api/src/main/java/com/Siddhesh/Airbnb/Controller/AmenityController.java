package com.Siddhesh.Airbnb.Controller;

import com.Siddhesh.Airbnb.DTOs.AmenityRequestDTO;
import com.Siddhesh.Airbnb.Model.Amenity;
import com.Siddhesh.Airbnb.Services.AmenityService;
import com.Siddhesh.Airbnb.Utility.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/amenity")
public class AmenityController {

    private final AmenityService amenityService;

    public AmenityController(AmenityService amenityService) {
        this.amenityService = amenityService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Amenity>>> fetchAllAmenities() {

        List<Amenity> amenities = amenityService.fetchAllAmenities();

        return ResponseEntity.ok(
                ApiResponse.success("Amenities fetched", amenities)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Amenity>> createAmenity(
            @RequestBody AmenityRequestDTO request
    ) {
        try {
            Amenity result = amenityService.insertAmenity(request.getName());
            return ResponseEntity.status(201)
                    .body(ApiResponse.success("Amenity created", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), "AMENITY_CREATE_FAILED"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Amenity>> fetchByAmenityId(@PathVariable long id) {

        Amenity amenity = amenityService.fetchAmenityById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Amenity fetched", amenity)
        );
    }
}