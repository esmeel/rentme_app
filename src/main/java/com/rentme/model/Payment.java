

package com.rentme.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "rental_id", referencedColumnName = "id")
  private Rental rental;

  private double amount;
  private double commission;

  @Enumerated(EnumType.STRING)
  private PaymentStatus status;

  private LocalDateTime createdAt;

  // Getters & Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Rental getRental() {
    return rental;
  }

  public void setRental(Rental rental) {
    this.rental = rental;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public double getCommission() {
    return commission;
  }

  public void setCommission(double commission) {
    this.commission = commission;
  }

  public PaymentStatus getStatus() {
    return status;
  }

  public void setStatus(PaymentStatus status) {
    this.status = status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
