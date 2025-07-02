package com.rentme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentme.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
