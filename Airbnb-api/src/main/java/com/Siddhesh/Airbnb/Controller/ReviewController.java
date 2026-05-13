package com.Siddhesh.Airbnb.Controller;

import com.Siddhesh.Airbnb.DTOs.ReviewDTO;
import com.Siddhesh.Airbnb.DTOs.ReviewRequestDTO;
import com.Siddhesh.Airbnb.Model.Review;
import com.Siddhesh.Airbnb.Services.ReviewService;
import com.Siddhesh.Airbnb.Utility.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> getAllReviews() {

        List<ReviewDTO> reviews = reviewService.getAllReviews();

        return ResponseEntity.ok(
                ApiResponse.success("Reviews fetched", reviews)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Review>> createReview(
            @RequestBody ReviewRequestDTO request
    ) {
        try {
            Review result = reviewService.insertReview(
                    request.getBookingId(),
                    request.getReviewerId(),
                    request.getRevieweeId(),
                    request.getPropertyId(),
                    request.getRating(),
                    request.getComment()
            );
            return ResponseEntity.status(201)
                    .body(ApiResponse.success("Review created", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), "REVIEW_CREATE_FAILED"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Review>> fetchByReviewId(@PathVariable long id) {

        Review review = reviewService.fetchReviewById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Review fetched", review)
        );
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> getReviewsByPropertyId(
            @PathVariable Long propertyId) {

        List<ReviewDTO> reviews = reviewService.getReviewsByPropertyId(propertyId);

        return ResponseEntity.ok(
                ApiResponse.success("Property reviews fetched", reviews)
        );
    }

    @GetMapping("/reviewer/{reviewerId}")
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> getReviewsByReviewerId(
            @PathVariable Long reviewerId) {

        List<ReviewDTO> reviews = reviewService.getReviewsByReviewerId(reviewerId);

        return ResponseEntity.ok(
                ApiResponse.success("Reviewer reviews fetched", reviews)
        );
    }

    @GetMapping("/reviewee/{revieweeId}")
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> getReviewsByRevieweeId(
            @PathVariable Long revieweeId) {

        List<ReviewDTO> reviews = reviewService.getReviewsByRevieweeId(revieweeId);

        return ResponseEntity.ok(
                ApiResponse.success("Reviewee reviews fetched", reviews)
        );
    }
}