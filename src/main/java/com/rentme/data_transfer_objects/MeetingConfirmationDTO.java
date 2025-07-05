package com.rentme.data_transfer_objects;

import com.rentme.model.NotificationType;

public class MeetingConfirmationDTO {
  private Long rentalId;
  private Long userId;
  private NotificationType notiType;

  public NotificationType getNotiType() {
    return this.notiType;
  }

  public void setNotiType(NotificationType notiType) {
    this.notiType = notiType;
  }

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
