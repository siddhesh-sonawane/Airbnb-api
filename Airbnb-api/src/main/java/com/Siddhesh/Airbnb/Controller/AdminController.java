package com.Siddhesh.Airbnb.Controller;

import com.Siddhesh.Airbnb.DTOs.BookingDTO;
import com.Siddhesh.Airbnb.DTOs.PropertyDTO;
import com.Siddhesh.Airbnb.Model.Booking;
import com.Siddhesh.Airbnb.Model.User;
import com.Siddhesh.Airbnb.Services.AdminService;
import com.Siddhesh.Airbnb.Utility.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ─── Users ─────────────────────────────────────────

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> fetchAllUsers() {

        List<User> users = adminService.fetchAllUsers();

        return ResponseEntity.ok(
                ApiResponse.success("All users fetched", users)
        );
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<User>> fetchUserById(@PathVariable Long id) {

        User user = adminService.fetchUserById(id);

        return ResponseEntity.ok(
                ApiResponse.success("User fetched", user)
        );
    }

    @PatchMapping("/users/{id}/role")
    public ResponseEntity<ApiResponse<User>> updateUserRole(
            @PathVariable Long id,
            @RequestParam User.Role role
    ) {
        User updated = adminService.updateUserRole(id, role);

        return ResponseEntity.ok(
                ApiResponse.success("User role updated", updated)
        );
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id) {

        adminService.deleteUser(id);

        return ResponseEntity.ok(
                ApiResponse.success("User deleted", "User with id " + id + " deleted")
        );
    }

    // ─── Properties ────────────────────────────────────

    @GetMapping("/properties")
    public ResponseEntity<ApiResponse<List<PropertyDTO>>> fetchAllProperties() {

        List<PropertyDTO> properties = adminService.fetchAllProperties();

        return ResponseEntity.ok(
                ApiResponse.success("All properties fetched", properties)
        );
    }

    @DeleteMapping("/properties/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProperty(@PathVariable Long id) {

        adminService.deleteProperty(id);

        return ResponseEntity.ok(
                ApiResponse.success("Property deleted", "Property with id " + id + " deleted")
        );
    }

    // ─── Bookings ──────────────────────────────────────

    @GetMapping("/bookings")
    public ResponseEntity<ApiResponse<List<BookingDTO>>> fetchAllBookings() {

        List<BookingDTO> bookings = adminService.fetchAllBookings();

        return ResponseEntity.ok(
                ApiResponse.success("All bookings fetched", bookings)
        );
    }

    @PatchMapping("/bookings/{id}/status")
    public ResponseEntity<ApiResponse<Booking>> updateBookingStatus(
            @PathVariable Long id,
            @RequestParam Booking.Status status
    ) {
        Booking updated = adminService.updateBookingStatus(id, status);

        return ResponseEntity.ok(
                ApiResponse.success("Booking status updated", updated)
        );
    }
}