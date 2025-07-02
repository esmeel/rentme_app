package com.rentme.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class ChatRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long chatRoomId;

  @ManyToOne
  private User owner;

  @ManyToOne
  private User renter;

  @OneToOne
  private Rental rental;

  private LocalDateTime createdAt;

  private boolean closed = false;

  public boolean isClosed() {
    return this.closed;
  }

  public boolean getClosed() {
    return this.closed;
  }

  public void setClosed(boolean closed) {
    this.closed = closed;
  }

  public Long getId() {
    return this.chatRoomId;
  }

  public void setId(Long chatRoomId) {
    this.chatRoomId = chatRoomId;
  }

  public User getOwner() {
    return this.owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public User getRenter() {
    return this.renter;
  }

  public void setRenter(User renter) {
    this.renter = renter;
  }

  public Rental getRental() {
    return this.rental;
  }

  public void setRental(Rental rental) {
    this.rental = rental;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

}
