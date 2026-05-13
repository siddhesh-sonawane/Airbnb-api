package com.Siddhesh.Airbnb.Repository;

import com.Siddhesh.Airbnb.Model.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByOwner_Id(Long ownerId);
    Page<Property> findAll(Pageable pageable);
}