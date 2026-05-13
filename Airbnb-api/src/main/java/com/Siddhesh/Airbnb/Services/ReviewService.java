package com.Siddhesh.Airbnb.Services;

import com.Siddhesh.Airbnb.DTOs.ReviewDTO;
import com.Siddhesh.Airbnb.Model.Booking;
import com.Siddhesh.Airbnb.Model.Property;
import com.Siddhesh.Airbnb.Model.Review;
import com.Siddhesh.Airbnb.Model.User;
import com.Siddhesh.Airbnb.Repository.ReviewRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @PersistenceContext
    EntityManager entityManager;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review insertReview(Long bookingId, Long reviewerId,
                               Long revieweeId, Long propertyId,
                               int rating, String comment) {

        Booking booking = entityManager.find(Booking.class, bookingId);
        if (booking == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
        }

        User reviewer = entityManager.find(User.class, reviewerId);
        if (reviewer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reviewer not found");
        }

        User reviewee = entityManager.find(User.class, revieweeId);
        if (reviewee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reviewee not found");
        }

        Property property = entityManager.find(Property.class, propertyId);
        if (property == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found");
        }

        boolean alreadyReviewed = reviewRepository
                .existsByBooking_IdAndReviewer_Id(bookingId, reviewerId);
        if (alreadyReviewed) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Review already submitted for this booking");
        }

        if (rating < 1 || rating > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Rating must be between 1 and 5");
        }

        Review review = new Review();
        review.setBooking(booking);
        review.setReviewer(reviewer);
        review.setReviewee(reviewee);
        review.setProperty(property);
        review.setRating(rating);
        review.setComment(comment);

        return reviewRepository.save(review);
    }

    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(review -> new ReviewDTO(
                        review.getId(),
                        review.getReviewer().getName(),
                        review.getReviewee().getName(),
                        review.getProperty().getTitle(),
                        review.getRating(),
                        review.getComment(),
                        review.getCreatedAt()
                ))
                .toList();
    }

    public Review fetchReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Review not found with id: " + id
                        )
                );
    }

    public List<ReviewDTO> getReviewsByPropertyId(Long propertyId) {
        List<Review> reviews = reviewRepository.findByProperty_Id(propertyId);

        return reviews.stream().map(review -> {

            long reviewCount = reviewRepository.countByProperty_Id(propertyId);

            return new ReviewDTO(
                    review.getId(),
                    review.getReviewer().getName(),
                    review.getReviewee().getName(),
                    review.getProperty().getTitle(),
                    review.getRating(),
                    review.getComment(),
                    review.getCreatedAt()
            );

        }).toList();
    }

    public List<ReviewDTO> getReviewsByReviewerId(Long reviewerId) {
        List<Review> reviews = reviewRepository.findByReviewer_Id(reviewerId);

        return reviews.stream().map(review -> new ReviewDTO(
                review.getId(),
                review.getReviewer().getName(),
                review.getReviewee().getName(),
                review.getProperty().getTitle(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        )).toList();
    }

    public List<ReviewDTO> getReviewsByRevieweeId(Long revieweeId) {
        List<Review> reviews = reviewRepository.findByReviewee_Id(revieweeId);

        return reviews.stream().map(review -> new ReviewDTO(
                review.getId(),
                review.getReviewer().getName(),
                review.getReviewee().getName(),
                review.getProperty().getTitle(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        )).toList();
    }
}