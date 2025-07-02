package com.rentme.data_transfer_objects;

public class ScheduleConfirmationRequest {
  private Long scheduleId;
  private Long rentalId;
  private Long userId;

  public ScheduleConfirmationRequest() {
  }

  public Long getScheduleId() {
    return scheduleId;
  }

  public void setScheduleId(Long scheduleId) {
    this.scheduleId = scheduleId;
  }

  public Long getRentalId() {
    return rentalId;
  }

  public void setRentalId(Long rentalId) {
    this.rentalId = rentalId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
