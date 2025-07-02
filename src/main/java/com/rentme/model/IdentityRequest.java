package com.rentme.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "identity_requests")
public class IdentityRequest {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(name = "document_type")
  private String documentType;

  @ElementCollection
  @CollectionTable(name = "identity_request_images", joinColumns = @JoinColumn(name = "request_id"))
  @Column(name = "image_url")
  private List<String> imageUrls;

  @Column(name = "status")
  private String status; // PENDING, APPROVED, REJECTED

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  private LocalDateTime submittedAt;

  private boolean verified;

  private LocalDateTime verificationTime;
  LocalDateTime reviewedAt;

  public boolean getVerified() {
    return this.verified;
  }


  public LocalDateTime getReviewedAt() {
    return this.reviewedAt;
  }

  public void setReviewedAt(LocalDateTime reviewedAt) {
    this.reviewedAt = reviewedAt;
  }


  @Column(length = 500)
  private String adminNote;

  public IdentityRequest() {}

  // Getters and setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDocumentType() {
    return documentType;
  }

  public void setDocumentType(String documentType) {
    this.documentType = documentType;
  }

  public List<String> getImageUrls() {
    return imageUrls;
  }

  public void setImageUrls(List<String> imageUrls) {
    this.imageUrls = imageUrls;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public LocalDateTime getSubmittedAt() {
    return submittedAt;
  }

  public void setSubmittedAt(LocalDateTime submittedAt) {
    this.submittedAt = submittedAt;
  }

  public boolean isVerified() {
    return verified;
  }

  public void setVerified(boolean verified) {
    this.verified = verified;
  }

  public LocalDateTime getVerificationTime() {
    return verificationTime;
  }

  public void setVerificationTime(LocalDateTime verificationTime) {
    this.verificationTime = verificationTime;
  }

  public String getAdminNote() {
    return adminNote;
  }

  public void setAdminNote(String adminNote) {
    this.adminNote = adminNote;
  }


}
