package com.rentme.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "schedule_entries")
public class ScheduleEntry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long senderId;
  private Long receiverId;
  private Long rentalId;
  private Long relatedId;

  private String date;

  private String fromTime;

  private String toTime;

  private String notes;

  private boolean confirmed;

  public ScheduleEntry() {}

  public Long getRelatedId() {
    return this.relatedId;
  }

  public void setRelatedId(Long relatedId) {
    this.relatedId = relatedId;
  }

  public String getTimeInfo() {
    return date + " from " + fromTime + " to " + toTime;
  }

  public Long getRentalId() {
    return this.rentalId;
  }

  public void setRentalId(Long rentalId) {
    this.rentalId = rentalId;
  }

  public boolean isConfirmed() {
    return this.confirmed;
  }

  public boolean getConfirmed() {
    return this.confirmed;
  }

  public void setConfirmed(boolean confirmed) {
    this.confirmed = confirmed;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getFromTime() {
    return fromTime;
  }

  public void setFromTime(String fromTime) {
    this.fromTime = fromTime;
  }

  public String getToTime() {
    return toTime;
  }

  public void setToTime(String toTime) {
    this.toTime = toTime;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  @Override
  public String toString() {
    return "{" + " id='" + getId() + "'" + ", senderId='" + getSenderId() + "'" + ", receiverId='"
        + getReceiverId() + "'" + ", rentalId='" + getRentalId() + "'" + ", relatedId='"
        + getRelatedId() + "'" + ", date='" + getDate() + "'" + ", fromTime='" + getFromTime() + "'"
        + ", toTime='" + getToTime() + "'" + ", notes='" + getNotes() + "'" + ", confirmed='"
        + isConfirmed() + "'" + "}";
  }



}
