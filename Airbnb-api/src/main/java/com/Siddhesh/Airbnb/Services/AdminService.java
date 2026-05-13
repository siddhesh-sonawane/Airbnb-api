package com.Siddhesh.Airbnb.Services;

import com.Siddhesh.Airbnb.DTOs.BookingDTO;
import com.Siddhesh.Airbnb.DTOs.PropertyDTO;
import com.Siddhesh.Airbnb.DTOs.PropertyImageDTO;
import com.Siddhesh.Airbnb.Model.Booking;
import com.Siddhesh.Airbnb.Model.Property;
import com.Siddhesh.Airbnb.Model.User;
import com.Siddhesh.Airbnb.Repository.BookingRepository;
import com.Siddhesh.Airbnb.Repository.PropertyImageRepository;
import com.Siddhesh.Airbnb.Repository.PropertyRepository;
import com.Siddhesh.Airbnb.Repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final BookingRepository bookingRepository;
    private final PropertyImageRepository propertyImageRepository;

    @PersistenceContext
    EntityManager entityManager;

    public AdminService(UserRepository userRepository,
                        PropertyRepository propertyRepository,
                        BookingRepository bookingRepository,
                        PropertyImageRepository propertyImageRepository) {
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
        this.bookingRepository = bookingRepository;
        this.propertyImageRepository = propertyImageRepository;
    }

    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

    public User fetchUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found with id: " + id
                        )
                );
    }

    public User updateUserRole(Long id, User.Role role) {
        User user = fetchUserById(id);
        user.setRole(role);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = fetchUserById(id);
        userRepository.delete(user);
    }

    public List<PropertyDTO> fetchAllProperties() {
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

    public void deleteProperty(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Property not found with id: " + id
                        )
                );
        propertyRepository.delete(property);
    }

    public List<BookingDTO> fetchAllBookings() {
        return bookingRepository.findAll().stream()
                .map(booking -> new BookingDTO(
                        booking.getId(),
                        booking.getUser().getName(),
                        booking.getProperty().getTitle(),
                        booking.getProperty().getAddress(),
                        booking.getCheckInDate(),
                        booking.getCheckOutDate(),
                        booking.getTotalPrice(),
                        booking.getStatus(),
                        booking.getCreatedAt()
                ))
                .toList();
    }

    public Booking updateBookingStatus(Long id, Booking.Status status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Booking not found with id: " + id
                        )
                );
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }
}