package com.Siddhesh.Airbnb.Services;

import com.Siddhesh.Airbnb.DTOs.AmenityDTO;
import com.Siddhesh.Airbnb.DTOs.PropertyAmenityDTO;
import com.Siddhesh.Airbnb.Model.Amenity;
import com.Siddhesh.Airbnb.Model.Property;
import com.Siddhesh.Airbnb.Model.PropertyAmenity;
import com.Siddhesh.Airbnb.Repository.PropertyAmenityRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PropertyAmenityService {

    private final PropertyAmenityRepository propertyAmenityRepository;

    @PersistenceContext
    EntityManager entityManager;

    public PropertyAmenityService(PropertyAmenityRepository propertyAmenityRepository) {
        this.propertyAmenityRepository = propertyAmenityRepository;
    }

    public PropertyAmenity insertPropertyAmenity(Long propertyId, Long amenityId) {

        Property property = entityManager.find(Property.class, propertyId);
        if (property == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found");
        }

        Amenity amenity = entityManager.find(Amenity.class, amenityId);
        if (amenity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Amenity not found");
        }

        boolean alreadyExists = propertyAmenityRepository
                .existsByProperty_IdAndAmenity_Id(propertyId, amenityId);
        if (alreadyExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Amenity already added to this property");
        }

        PropertyAmenity propertyAmenity = new PropertyAmenity();
        propertyAmenity.setProperty(property);
        propertyAmenity.setAmenity(amenity);

        return propertyAmenityRepository.save(propertyAmenity);
    }

    public List<PropertyAmenityDTO> getAllPropertyAmenities() {
        return propertyAmenityRepository.findAll().stream()
                .map(pa -> new PropertyAmenityDTO(
                        pa.getProperty().getId(),
                        pa.getProperty().getTitle(),
                        pa.getProperty().getAddress(),
                        propertyAmenityRepository.countByProperty_Id(pa.getProperty().getId()),
                        propertyAmenityRepository
                                .findByProperty_Id(pa.getProperty().getId())
                                .stream()
                                .map(a -> new AmenityDTO(
                                        a.getAmenity().getId(),
                                        a.getAmenity().getName()
                                ))
                                .toList()
                ))
                .toList();
    }

    public PropertyAmenity fetchPropertyAmenityById(Long id) {
        return propertyAmenityRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Property amenity not found with id: " + id
                        )
                );
    }

    public List<PropertyAmenityDTO> getAmenitiesByPropertyId(Long propertyId) {
        List<PropertyAmenity> propertyAmenities =
                propertyAmenityRepository.findByProperty_Id(propertyId);

        long amenityCount = propertyAmenityRepository.countByProperty_Id(propertyId);

        List<AmenityDTO> amenities = propertyAmenities.stream()
                .map(pa -> new AmenityDTO(
                        pa.getAmenity().getId(),
                        pa.getAmenity().getName()
                ))
                .toList();

        if (propertyAmenities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No amenities found for property with id: " + propertyId);
        }

        PropertyAmenity first = propertyAmenities.get(0);

        return List.of(new PropertyAmenityDTO(
                first.getProperty().getId(),
                first.getProperty().getTitle(),
                first.getProperty().getAddress(),
                amenityCount,
                amenities
        ));
    }
}