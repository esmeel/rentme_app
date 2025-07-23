package com.rentme.data_transfer_objects;

import com.rentme.model.NotificationType;

public class MeetingConfirmationDTO {
  private Long rentalId;
  private Long userId;
  private NotificationType notiType;
  private java.time.LocalDate starts;
  private java.time.LocalDate ends;
  private String meetingDate;
  private String meetingHourFrom;

  private String meetingHourTo;

  private java.time.LocalDate notes;

  public MeetingConfirmationDTO() {}

  public MeetingConfirmationDTO(Long rentalId, Long userId) {
    this.rentalId = rentalId;
    this.userId = userId;
  }

  public String getMeetingDate() {
    return this.meetingDate;
  }

  public void setMeetingDate(String meetingDate) {
    this.meetingDate = meetingDate;
  }

  public String getMeetingHourFrom() {
    return this.meetingHourFrom;
  }

  public void setMeetingHourFrom(String meetingHourFrom) {
    this.meetingHourFrom = meetingHourFrom;
  }

  public String getMeetingHourTo() {
    return this.meetingHourTo;
  }

  public void setMeetingHourTo(String meetingHourTo) {
    this.meetingHourTo = meetingHourTo;
  }

  public java.time.LocalDate getNotes() {
    return this.notes;
  }

  public void setNotes(java.time.LocalDate notes) {
    this.notes = notes;
  }

  public java.time.LocalDate getStarts() {
    return this.starts;
  }

  public void setStarts(java.time.LocalDate starts) {
    this.starts = starts;
  }

  public java.time.LocalDate getEnds() {
    return this.ends;
  }

  public void setEnds(java.time.LocalDate ends) {
    this.ends = ends;
  }

  // getters and setters

  public NotificationType getNotiType() {
    return this.notiType;
  }

  public void setNotiType(NotificationType notiType) {
    this.notiType = notiType;
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
    return "{" + " scheduleEntryId='" + getRentalId() + "'" + ", userId='" + getUserId() + "'"
        + "}";
  }
}
