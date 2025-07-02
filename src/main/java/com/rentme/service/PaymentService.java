package com.rentme.service;

import com.rentme.model.Payment;
import com.rentme.model.PaymentStatus;
import com.rentme.model.Rental;
import com.rentme.repository.PaymentRepository;
import com.rentme.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

  private final PaymentRepository paymentRepository;
  private final RentalRepository rentalRepository;

  public PaymentService(PaymentRepository paymentRepository, RentalRepository rentalRepository) {
    this.paymentRepository = paymentRepository;
    this.rentalRepository = rentalRepository;
  }

  public Payment createPayment(Long rentalId, double amount) {
    Rental rental = rentalRepository.findById(rentalId)
        .orElseThrow(() -> new RuntimeException("Rental not found"));

    double commission = amount * 0.02; // 2% commission

    Payment payment = new Payment();
    payment.setRental(rental);
    payment.setAmount(amount);
    payment.setCommission(commission);
    payment.setStatus(PaymentStatus.PENDING);
    payment.setCreatedAt(LocalDateTime.now());

    return paymentRepository.save(payment);
  }

  public Payment updatePaymentStatus(Long paymentId, PaymentStatus status) {
    Payment payment = paymentRepository.findById(paymentId)
        .orElseThrow(() -> new RuntimeException("Payment not found"));

    payment.setStatus(status);
    return paymentRepository.save(payment);
  }
}
