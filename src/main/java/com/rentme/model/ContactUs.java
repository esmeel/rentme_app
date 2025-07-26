package com.rentme.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "contact_us")
public class ContactUs {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;
  private String subject;
  private String message;
  private Long userId;
  private String userName;
  private LocalDate sentAt;

  @Enumerated(EnumType.STRING)
  private MessageStatus status;

  public ContactUs() {}

  public void setId(Long id) {
    this.id = id;
  }



  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSubject() {
    return this.subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Long getUserId() {
    return this.userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public LocalDate getSentAt() {
    return this.sentAt;
  }

  public void setSentAt(LocalDate sentAt) {
    this.sentAt = sentAt;
  }

  public MessageStatus getStatus() {
    return this.status;
  }

  public void setStatus(MessageStatus status) {
    this.status = status;
  }


  @Override
  public String toString() {
    return "{" + " email='" + getEmail() + "'" + ", subject='" + getSubject() + "'" + ", message='"
        + getMessage() + "'" + ", userId='" + getUserId() + "'" + ", userName='" + getUserName()
        + "'" + ", sentAt='" + getSentAt() + "'" + ", status='" + getStatus() + "'" + "}";
  }


  public Long getId() {
    return this.id;
  }


}
