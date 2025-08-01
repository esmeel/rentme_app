package com.rentme.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_reviews")
public class UserReview {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private int rating;

  @Column(name = "reviewer_id")
  private Long reviewerId;

  @Column(name = "target_user_id")
  private Long targetUserId;

  // Getters & Setters

  public Long getReviewerId() {
    return this.reviewerId;
  }

  public void setReviewerId(Long reviewerId) {
    this.reviewerId = reviewerId;
  }

  public Long getTargetUserId() {
    return this.targetUserId;
  }

  public void setTargetUserId(Long targetUserId) {
    this.targetUserId = targetUserId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }



}
