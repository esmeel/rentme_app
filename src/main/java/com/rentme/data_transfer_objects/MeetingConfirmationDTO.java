package com.rentme.data_transfer_objects;

public class MeetingConfirmationDTO {
  private Long rentalId;
  private Long userId;

  // getters and setters

  public MeetingConfirmationDTO() {
  }

  public MeetingConfirmationDTO(Long rentalId, Long userId) {
    this.rentalId = rentalId;
    this.userId = userId;
  }

  public Long getRentalId() {
    return this.rentalId;
  }

  public void setRentalId(Long rentalId) {
    this.rentalId = rentalId;
  }

  public Long getUserId() {
    return this.userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public MeetingConfirmationDTO scheduleEntryId(Long rentalId) {
    setRentalId(rentalId);
    return this;
  }

  public MeetingConfirmationDTO userId(Long userId) {
    setUserId(userId);
    return this;
  }

  @Override
  public String toString() {
    return "{" +
        " scheduleEntryId='" + getRentalId() + "'" +
        ", userId='" + getUserId() + "'" +
        "}";
  }
}
