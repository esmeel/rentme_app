package com.rentme.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class RentalRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private User renter; // المستأجر

  @ManyToOne
  private User owner; // المالك

  @ManyToOne
  private Tool tool;

  @Enumerated(EnumType.STRING)
  private RentalStatus status = RentalStatus.PENDING;

  private LocalDateTime requestDate = LocalDateTime.now();

  // لاحقًا نضيف: startDate, endDate, price...

  // Getters and setters...
}
