package com.Siddhesh.Airbnb.Repository;

import com.Siddhesh.Airbnb.Model.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyImageRepository extends JpaRepository<PropertyImage, Long> {
    long countByProperty_Id(Long propertyId);
    List<PropertyImage> findByProperty_Id(Long propertyId);
}