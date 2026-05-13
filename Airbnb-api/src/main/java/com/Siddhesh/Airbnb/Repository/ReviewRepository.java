package com.Siddhesh.Airbnb.Repository;

import com.Siddhesh.Airbnb.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProperty_Id(Long propertyId);
    List<Review> findByReviewer_Id(Long reviewerId);
    List<Review> findByReviewee_Id(Long revieweeId);
    long countByProperty_Id(Long propertyId);
    boolean existsByBooking_IdAndReviewer_Id(Long bookingId, Long reviewerId);
}