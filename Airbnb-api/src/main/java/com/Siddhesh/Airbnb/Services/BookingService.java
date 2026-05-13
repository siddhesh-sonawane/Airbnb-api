package com.Siddhesh.Airbnb.Services;

import com.Siddhesh.Airbnb.DTOs.BookingDTO;
import com.Siddhesh.Airbnb.Model.Booking;
import com.Siddhesh.Airbnb.Model.Property;
import com.Siddhesh.Airbnb.Model.User;
import com.Siddhesh.Airbnb.Repository.BookingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    @PersistenceContext
    EntityManager entityManager;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }


    public Booking insertBooking(Long userId, Long propertyId,
                                 LocalDate checkInDate, LocalDate checkOutDate) {

        User user = entityManager.find(User.class, userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        Property property = entityManager.find(Property.class, propertyId);
        if (property == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found");
        }

        if (!checkOutDate.isAfter(checkInDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Check-out date must be after check-in date");
        }

        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        BigDecimal basePrice = property.getPricePerNight()
                .multiply(BigDecimal.valueOf(nights));

        BigDecimal cleaningFee = property.getCleaningFee();
        BigDecimal serviceFee = property.getServiceFee();
        BigDecimal totalPrice = basePrice.add(cleaningFee).add(serviceFee);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setProperty(property);
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);
        booking.setBasePrice(basePrice);
        booking.setCleaningFee(cleaningFee);
        booking.setServiceFee(serviceFee);
        booking.setTotalPrice(totalPrice);
        booking.setStatus(Booking.Status.PENDING);

        return bookingRepository.save(booking);
    }

    public List<BookingDTO> getAllBookings() {
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

    public Booking fetchBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Booking not found with id: " + id
                        )
                );
    }

    public List<BookingDTO> getBookingsByUserId(Long userId) {
        List<Booking> bookings = bookingRepository.findByUser_Id(userId);

        return bookings.stream().map(booking -> {

            long bookingCount = bookingRepository.countByProperty_Id(booking.getProperty().getId());

            return new BookingDTO(
                    booking.getId(),
                    booking.getUser().getName(),
                    booking.getProperty().getTitle(),
                    booking.getProperty().getAddress(),
                    booking.getCheckInDate(),
                    booking.getCheckOutDate(),
                    booking.getTotalPrice(),
                    booking.getStatus(),
                    booking.getCreatedAt()
            );

        }).toList();
    }

    public List<BookingDTO> getBookingsByPropertyId(Long propertyId) {
        List<Booking> bookings = bookingRepository.findByProperty_Id(propertyId);

        return bookings.stream().map(booking -> new BookingDTO(
                booking.getId(),
                booking.getUser().getName(),
                booking.getProperty().getTitle(),
                booking.getProperty().getAddress(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getTotalPrice(),
                booking.getStatus(),
                booking.getCreatedAt()
        )).toList();
    }

    public Booking updateBookingStatus(Long id, Booking.Status status) {
        Booking booking = fetchBookingById(id);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    public Page<BookingDTO> getAllBookingsPaginated(Pageable pageable) {
        return bookingRepository.findAll(pageable)
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
                ));
    }
}