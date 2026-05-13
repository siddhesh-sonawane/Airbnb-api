package com.Siddhesh.Airbnb.Controller;

import com.Siddhesh.Airbnb.DTOs.BookingDTO;
import com.Siddhesh.Airbnb.DTOs.BookingRequestDTO;
import com.Siddhesh.Airbnb.Model.Booking;
import com.Siddhesh.Airbnb.Services.BookingService;
import com.Siddhesh.Airbnb.Utility.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookingDTO>>> getAllBookings() {

        List<BookingDTO> bookings = bookingService.getAllBookings();

        return ResponseEntity.ok(
                ApiResponse.success("Bookings fetched", bookings)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Booking>> createBooking(
            @RequestBody BookingRequestDTO request
    ) {
        try {
            Booking result = bookingService.insertBooking(
                    request.getUserId(),
                    request.getPropertyId(),
                    request.getCheckInDate(),
                    request.getCheckOutDate()
            );
            return ResponseEntity.status(201)
                    .body(ApiResponse.success("Booking created", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), "BOOKING_CREATE_FAILED"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Booking>> fetchByBookingId(@PathVariable long id) {

        Booking booking = bookingService.fetchBookingById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Booking fetched", booking)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<BookingDTO>>> getBookingsByUserId(@PathVariable Long userId) {

        List<BookingDTO> bookings = bookingService.getBookingsByUserId(userId);

        return ResponseEntity.ok(
                ApiResponse.success("User bookings fetched", bookings)
        );
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<ApiResponse<List<BookingDTO>>> getBookingsByPropertyId(@PathVariable Long propertyId) {

        List<BookingDTO> bookings = bookingService.getBookingsByPropertyId(propertyId);

        return ResponseEntity.ok(
                ApiResponse.success("Property bookings fetched", bookings)
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Booking>> updateBookingStatus(
            @PathVariable Long id,
            @RequestParam Booking.Status status
    ) {
        Booking updated = bookingService.updateBookingStatus(id, status);

        return ResponseEntity.ok(
                ApiResponse.success("Booking status updated", updated)
        );
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<BookingDTO>>> getAllBookingsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<BookingDTO> bookings = bookingService.getAllBookingsPaginated(pageable);

        return ResponseEntity.ok(
                ApiResponse.success("Bookings fetched paginated", bookings)
        );
    }
}