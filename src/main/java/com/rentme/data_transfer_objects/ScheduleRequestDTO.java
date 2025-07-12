package com.rentme.data_transfer_objects;

import java.util.List;

public class ScheduleRequestDTO {
  private Long senderId;
  private Long receiverId;
  private Long rentalId;
  private Long relatedId;
  private String notes;

  private java.time.LocalDate starts;
  private java.time.LocalDate ends;

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

  private List<ScheduleEntryDTO> entries;

  public ScheduleRequestDTO() {}

  public Long getRelatedId() {
    return this.relatedId;
  }

  public void setRelatedId(Long relatedId) {
    this.relatedId = relatedId;
  }

  public Long getRentalId() {
    return this.rentalId;
  }

  public void setRentalId(Long rentalId) {
    this.rentalId = rentalId;
  }

  public Long getSenderId() {
    return senderId;
  }

  public void setSenderId(Long senderId) {
    this.senderId = senderId;
  }

  public Long getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(Long receiverId) {
    this.receiverId = receiverId;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public List<ScheduleEntryDTO> getEntries() {
    return entries;
  }

  public void setEntries(List<ScheduleEntryDTO> entries) {
    this.entries = entries;
  }
}
