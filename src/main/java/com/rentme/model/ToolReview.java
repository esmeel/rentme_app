package com.rentme.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tool_reviews")
public class ToolReview {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String comment;
  private int rating;

  @ManyToOne
  @JoinColumn(name = "reviewer_id")
  private User reviewer;

  @ManyToOne
  @JoinColumn(name = "tool_id")
  private Tool tool;

  // Getters & Setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
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



  public Tool getTool() {
    return tool;
  }

  public void setTool(Tool tool) {
    this.tool = tool;
  }



  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", comment='" + getComment() + "'" +
        ", rating='" + getRating() + "'" +
        ", reviewer='" + getReviewer() + "'" +
        ", tool='" + getTool() + "'" +
        "}";
  }



}
