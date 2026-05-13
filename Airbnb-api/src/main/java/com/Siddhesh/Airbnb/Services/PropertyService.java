package com.Siddhesh.Airbnb.Services;

import com.Siddhesh.Airbnb.DTOs.PropertyDTO;
import com.Siddhesh.Airbnb.DTOs.PropertyImageDTO;
import com.Siddhesh.Airbnb.Model.Property;
import com.Siddhesh.Airbnb.Model.PropertyImage;
import com.Siddhesh.Airbnb.Model.User;
import com.Siddhesh.Airbnb.Repository.PropertyImageRepository;
import com.Siddhesh.Airbnb.Repository.PropertyRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyImageRepository propertyImageRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public PropertyService(PropertyRepository propertyRepository,
                           PropertyImageRepository propertyImageRepository) {
        this.propertyRepository = propertyRepository;
        this.propertyImageRepository = propertyImageRepository;
    }


    public Property insertProperty(Long ownerId, String title, String description,
                                   String address, Double latitude, Double longitude,
                                   BigDecimal pricePerNight, Integer maxGuests,
                                   BigDecimal cleaningFee, BigDecimal serviceFee,
                                   Property.CancellationPolicy cancellationPolicy,
                                   List<MultipartFile> images) throws IOException {

        User owner = entityManager.find(User.class, ownerId);
        if (owner == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        Property property = new Property();
        property.setOwner(owner);
        property.setTitle(title);
        property.setDescription(description);
        property.setAddress(address);
        property.setLatitude(latitude);
        property.setLongitude(longitude);
        property.setPricePerNight(pricePerNight);
        property.setMaxGuests(maxGuests);
        property.setCleaningFee(cleaningFee != null ? cleaningFee : BigDecimal.ZERO);
        property.setServiceFee(serviceFee != null ? serviceFee : BigDecimal.ZERO);
        property.setCancellationPolicy(cancellationPolicy != null ?
                cancellationPolicy : Property.CancellationPolicy.FLEXIBLE);

        Property saved = propertyRepository.save(property);

        if (images != null) {
            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
                    File destination = new File(uploadDir + File.separator + fileName);
                    image.transferTo(destination);

                    PropertyImage img = new PropertyImage();
                    img.setProperty(saved);
                    img.setUrl(fileName);
                    propertyImageRepository.save(img);
                    saved.getImages().add(img);
                }
            }
        }

        return saved;
    }

    public List<PropertyDTO> getAllProperties() {
        return propertyRepository.findAll().stream()
                .map(property -> new PropertyDTO(
                        property.getId(),
                        property.getTitle(),
                        property.getDescription(),
                        property.getAddress(),
                        property.getLatitude(),
                        property.getLongitude(),
                        property.getPricePerNight(),
                        property.getMaxGuests(),
                        property.getOwner().getName(),
                        property.getImages().size(),
                        property.getImages()
                                .stream()
                                .map(img -> new PropertyImageDTO(img.getId(), img.getUrl()))
                                .toList()
                ))
                .toList();
    }

    public Property fetchPropertyById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Property not found with id: " + id
                        )
                );
    }

    public List<PropertyDTO> getPropertiesByOwnerId(Long ownerId) {
        List<Property> properties = propertyRepository.findByOwner_Id(ownerId);

        return properties.stream().map(property -> {

            long imageCount = propertyImageRepository.countByProperty_Id(property.getId());

            List<PropertyImageDTO> images = property.getImages()
                    .stream()
                    .map(img -> new PropertyImageDTO(img.getId(), img.getUrl()))
                    .toList();

            return new PropertyDTO(
                    property.getId(),
                    property.getTitle(),
                    property.getDescription(),
                    property.getAddress(),
                    property.getLatitude(),
                    property.getLongitude(),
                    property.getPricePerNight(),
                    property.getMaxGuests(),
                    property.getOwner().getName(),
                    imageCount,
                    images
            );

        }).toList();
    }

    public List<PropertyDTO> getAllPropertiesWithImages() {
        List<Property> properties = propertyRepository.findAll();

        return properties.stream().map(property -> {

            long imageCount = propertyImageRepository.countByProperty_Id(property.getId());

            List<PropertyImageDTO> images = property.getImages()
                    .stream()
                    .map(img -> new PropertyImageDTO(img.getId(), img.getUrl()))
                    .toList();

            return new PropertyDTO(
                    property.getId(),
                    property.getTitle(),
                    property.getDescription(),
                    property.getAddress(),
                    property.getLatitude(),
                    property.getLongitude(),
                    property.getPricePerNight(),
                    property.getMaxGuests(),
                    property.getOwner().getName(),
                    imageCount,
                    images
            );

        }).toList();
    }

    public Page<PropertyDTO> getAllPropertiesPaginated(Pageable pageable) {
        return propertyRepository.findAll(pageable)
                .map(property -> new PropertyDTO(
                        property.getId(),
                        property.getTitle(),
                        property.getDescription(),
                        property.getAddress(),
                        property.getLatitude(),
                        property.getLongitude(),
                        property.getPricePerNight(),
                        property.getMaxGuests(),
                        property.getOwner().getName(),
                        property.getImages().size(),
                        property.getImages()
                                .stream()
                                .map(img -> new PropertyImageDTO(img.getId(), img.getUrl()))
                                .toList()
                ));
    }
}