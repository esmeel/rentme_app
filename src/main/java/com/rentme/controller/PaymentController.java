package com.rentme.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentme.model.Payment;
import com.rentme.model.PaymentStatus;
import com.rentme.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping
  public ResponseEntity<Payment> createPayment(@RequestParam Long rentalId,
      @RequestParam double amount) {
    Payment payment = paymentService.createPayment(rentalId, amount);
    return ResponseEntity.ok(payment);
  }

  @PutMapping("/{paymentId}/status")
  public ResponseEntity<Payment> updatePaymentStatus(@PathVariable Long paymentId,
      @RequestParam PaymentStatus status) {
    Payment payment = paymentService.updatePaymentStatus(paymentId, status);
    return ResponseEntity.ok(payment);
  }
}
