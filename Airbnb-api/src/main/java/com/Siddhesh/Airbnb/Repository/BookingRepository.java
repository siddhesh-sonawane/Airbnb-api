package com.Siddhesh.Airbnb.Repository;

import com.Siddhesh.Airbnb.Model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser_Id(Long userId);
    List<Booking> findByProperty_Id(Long propertyId);
    long countByProperty_Id(Long propertyId);
    Page<Booking> findAll(Pageable pageable);
}