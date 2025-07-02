package com.rentme.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_reviews")
public class UserReview {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private int rating; // بدون تعليق

  @ManyToOne
  @JoinColumn(name = "reviewer_id")
  private User reviewer;

  @ManyToOne
  @JoinColumn(name = "target_user_id")
  private User targetUser;

  // Getters & Setters

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

  public User getReviewer() {
    return reviewer;
  }

  public void setReviewer(User reviewer) {
    this.reviewer = reviewer;
  }

  public User getTargetUser() {
    return targetUser;
  }

  public void setTargetUser(User targetUser) {
    this.targetUser = targetUser;
  }


  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", rating='" + getRating() + "'" +
        ", reviewer='" + getReviewer() + "'" +
        ", targetUser='" + getTargetUser() + "'" +
        "}";
  }


}
