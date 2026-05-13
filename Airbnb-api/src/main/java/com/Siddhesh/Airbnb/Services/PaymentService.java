package com.Siddhesh.Airbnb.Services;

import com.Siddhesh.Airbnb.DTOs.PaymentDTO;
import com.Siddhesh.Airbnb.Model.Booking;
import com.Siddhesh.Airbnb.Model.Payment;
import com.Siddhesh.Airbnb.Repository.BookingRepository;
import com.Siddhesh.Airbnb.Repository.PaymentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    @PersistenceContext
    EntityManager entityManager;

    public PaymentService(PaymentRepository paymentRepository,
                          BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
    }

    public Payment insertPayment(Long bookingId, String paymentMethod) {

        Booking booking = entityManager.find(Booking.class, bookingId);
        if (booking == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
        }

        boolean alreadyPaid = paymentRepository
                .existsByBooking_IdAndStatus(bookingId, Payment.Status.SUCCESS);
        if (alreadyPaid) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Payment already completed for this booking");
        }

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(booking.getTotalPrice());
        payment.setPaymentMethod(paymentMethod);
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setStatus(Payment.Status.SUCCESS);

        Payment saved = paymentRepository.save(payment);

        booking.setStatus(Booking.Status.CONFIRMED);
        bookingRepository.save(booking);

        return saved;
    }

    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(payment -> new PaymentDTO(
                        payment.getId(),
                        payment.getBooking().getUser().getName(),
                        payment.getBooking().getProperty().getTitle(),
                        payment.getAmount(),
                        payment.getStatus(),
                        payment.getPaymentMethod(),
                        payment.getTransactionId(),
                        payment.getCreatedAt()
                ))
                .toList();
    }

    public Payment fetchPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Payment not found with id: " + id
                        )
                );
    }

    public List<PaymentDTO> getPaymentsByBookingId(Long bookingId) {
        List<Payment> payments = paymentRepository.findByBooking_Id(bookingId);

        return payments.stream().map(payment -> {

            long paymentCount = paymentRepository.countByBooking_Id(bookingId);

            return new PaymentDTO(
                    payment.getId(),
                    payment.getBooking().getUser().getName(),
                    payment.getBooking().getProperty().getTitle(),
                    payment.getAmount(),
                    payment.getStatus(),
                    payment.getPaymentMethod(),
                    payment.getTransactionId(),
                    payment.getCreatedAt()
            );

        }).toList();
    }

    public Payment updatePaymentStatus(Long id, Payment.Status status) {
        Payment payment = fetchPaymentById(id);
        payment.setStatus(status);

        if (status == Payment.Status.REFUNDED) {
            Booking booking = payment.getBooking();
            booking.setStatus(Booking.Status.CANCELLED);
            bookingRepository.save(booking);
        }

        return paymentRepository.save(payment);
    }
}