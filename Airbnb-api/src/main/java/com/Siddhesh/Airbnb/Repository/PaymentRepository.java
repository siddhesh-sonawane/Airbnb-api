package com.Siddhesh.Airbnb.Repository;

import com.Siddhesh.Airbnb.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByBooking_Id(Long bookingId);
    Optional<Payment> findByTransactionId(String transactionId);
    long countByBooking_Id(Long bookingId);
    boolean existsByBooking_IdAndStatus(Long bookingId, Payment.Status status);
}