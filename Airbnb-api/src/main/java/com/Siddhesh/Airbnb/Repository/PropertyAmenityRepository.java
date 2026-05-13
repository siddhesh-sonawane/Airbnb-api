package com.Siddhesh.Airbnb.Repository;

import com.Siddhesh.Airbnb.Model.PropertyAmenity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyAmenityRepository extends JpaRepository<PropertyAmenity, Long> {
    List<PropertyAmenity> findByProperty_Id(Long propertyId);
    long countByProperty_Id(Long propertyId);
    boolean existsByProperty_IdAndAmenity_Id(Long propertyId, Long amenityId);
}