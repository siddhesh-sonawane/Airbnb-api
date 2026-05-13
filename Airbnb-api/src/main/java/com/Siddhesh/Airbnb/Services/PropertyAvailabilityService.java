package com.Siddhesh.Airbnb.Services;

import com.Siddhesh.Airbnb.Model.Property;
import com.Siddhesh.Airbnb.Model.PropertyAvailability;
import com.Siddhesh.Airbnb.Repository.PropertyAvailabilityRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PropertyAvailabilityService {

    private final PropertyAvailabilityRepository propertyAvailabilityRepository;

    @PersistenceContext
    EntityManager entityManager;

    public PropertyAvailabilityService(PropertyAvailabilityRepository propertyAvailabilityRepository) {
        this.propertyAvailabilityRepository = propertyAvailabilityRepository;
    }

    public PropertyAvailability insertAvailability(PropertyAvailability availability) {

        Long propertyId = availability.getProperty().getId();

        Property property = entityManager.find(Property.class, propertyId);
        if (property == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found");
        }

        availability.setProperty(property);

        return propertyAvailabilityRepository.save(availability);
    }

    public List<PropertyAvailability> fetchAllAvailability() {
        return propertyAvailabilityRepository.findAll();
    }

    public PropertyAvailability fetchAvailabilityById(Long id) {
        return propertyAvailabilityRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Availability not found with id: " + id
                        )
                );
    }

    public List<PropertyAvailability> getAvailabilityByPropertyId(Long propertyId) {
        return propertyAvailabilityRepository.findByProperty_Id(propertyId);
    }
}