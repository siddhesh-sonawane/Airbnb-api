package com.Siddhesh.Airbnb.Controller;

import com.Siddhesh.Airbnb.DTOs.PaymentDTO;
import com.Siddhesh.Airbnb.Model.Payment;
import com.Siddhesh.Airbnb.Services.PaymentService;
import com.Siddhesh.Airbnb.Utility.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentDTO>>> getAllPayments() {

        List<PaymentDTO> payments = paymentService.getAllPayments();

        return ResponseEntity.ok(
                ApiResponse.success("Payments fetched", payments)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Payment>> createPayment(
            @RequestParam Long bookingId,
            @RequestParam String paymentMethod
    ) {
        try {
            Payment result = paymentService.insertPayment(bookingId, paymentMethod);
            return ResponseEntity.status(201)
                    .body(ApiResponse.success("Payment created", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), "PAYMENT_CREATE_FAILED"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Payment>> fetchByPaymentId(@PathVariable long id) {

        Payment payment = paymentService.fetchPaymentById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Payment fetched", payment)
        );
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<ApiResponse<List<PaymentDTO>>> getPaymentsByBookingId(
            @PathVariable Long bookingId) {

        List<PaymentDTO> payments = paymentService.getPaymentsByBookingId(bookingId);

        return ResponseEntity.ok(
                ApiResponse.success("Booking payments fetched", payments)
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Payment>> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam Payment.Status status
    ) {
        Payment updated = paymentService.updatePaymentStatus(id, status);

        return ResponseEntity.ok(
                ApiResponse.success("Payment status updated", updated)
        );
    }
}