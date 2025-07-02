package com.rentme.data_transfer_objects;

public class UserReviewRequestDTO {
  private Long targetUserId;
  private Long reviewerId;
  private int rating;


  public Long getTargetUserId() {
    return this.targetUserId;
  }


  public void setTargetUserId(Long targetUserId) {
    this.targetUserId = targetUserId;
  }

  public Long getReviewerId() {
    return this.reviewerId;
  }

  public void setReviewerId(Long reviewerId) {
    this.reviewerId = reviewerId;
  }

  public int getRating() {
    return this.rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }



  // getters & setters
}
