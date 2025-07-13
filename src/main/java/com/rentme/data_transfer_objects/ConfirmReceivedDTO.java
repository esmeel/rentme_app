package com.rentme.data_transfer_objects;

public class ConfirmReceivedDTO {
  private Long rentalId;
  private Long UserId;

  public Long getUserId() {
    return this.UserId;
  }

  public void setUserId(Long UserId) {
    this.UserId = UserId;
  }

  public Long getRentalId() {
    return rentalId;
  }

  public void setRentalId(Long rentalId) {
    this.rentalId = rentalId;
  }
}
