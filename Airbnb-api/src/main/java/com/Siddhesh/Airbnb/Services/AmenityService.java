package com.Siddhesh.Airbnb.Services;

import com.Siddhesh.Airbnb.Model.Amenity;
import com.Siddhesh.Airbnb.Repository.AmenityRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AmenityService {

    private final AmenityRepository amenityRepository;

    @PersistenceContext
    EntityManager entityManager;

    public AmenityService(AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    public Amenity insertAmenity(String name) {

        boolean alreadyExists = amenityRepository.existsByName(name);
        if (alreadyExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Amenity already exists with name: " + name);
        }

        Amenity amenity = new Amenity();
        amenity.setName(name);

        return amenityRepository.save(amenity);
    }

    public List<Amenity> fetchAllAmenities() {
        return amenityRepository.findAll();
    }

    public Amenity fetchAmenityById(Long id) {
        return amenityRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Amenity not found with id: " + id
                        )
                );
    }
}