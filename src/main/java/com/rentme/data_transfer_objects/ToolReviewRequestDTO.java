package com.rentme.data_transfer_objects;


public class ToolReviewRequestDTO {
  private Long toolId;
  private int rating;
  private String comment;
  private Long reviewerId;

  // Getters & Setters


  public Long getToolId() {
    return this.toolId;
  }

  public void setToolId(Long toolId) {
    this.toolId = toolId;
  }

  public int getRating() {
    return this.rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Long getReviewerId() {
    return this.reviewerId;
  }

  public void setReviewerId(Long reviewerId) {
    this.reviewerId = reviewerId;
  }
}
