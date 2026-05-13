package com.Siddhesh.Airbnb.Repository;

import com.Siddhesh.Airbnb.Model.PropertyAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyAvailabilityRepository extends JpaRepository<PropertyAvailability, Long> {
    List<PropertyAvailability> findByProperty_Id(Long propertyId);
}