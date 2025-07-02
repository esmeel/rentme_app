package com.rentme.data_transfer_objects;

import com.rentme.model.ToolReview;

public class ToolReviewResponseDTO {
  private String comment;
  private int rating;
  private String reviewerName;

  public ToolReviewResponseDTO(ToolReview review) {
    this.comment = review.getComment();
    this.rating = review.getRating();
    this.reviewerName = review.getReviewer() != null
        ? review.getReviewer().getName()
        : "Unknown";
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

  public String getReviewerName() {
    return reviewerName;
  }

  public void setReviewerName(String reviewerName) {
    this.reviewerName = reviewerName;
  }
}
