package com.rentme.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification")
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long receiverId;
  private String receiverName;
  private Long senderId;
  private String senderName;
  private String message;
  private String toolPicUrl;
  private String toolName;
  private String address;
  private String notes;
  @Column(name = "meeting")
  private String meeting;

  @Column(name = "starts")
  private java.time.LocalDate starts;

  @Column(name = "ends")
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



  public String getMeeting() {
    return this.meeting;
  }

  public void setMeeting(String meeting) {
    this.meeting = meeting;
  }

  Double latitude;

  Double longitude;

  boolean locationRequested = false;
  boolean timeRequested = false;

  @Enumerated(EnumType.STRING)
  private NotificationType type;

  @Column(name = "is_read")
  private boolean read;

  private Long relatedId;

  private LocalDateTime createdAt = LocalDateTime.now();

  public String getNotes() {
    return this.notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Double getLatitude() {
    return this.latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return this.longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public boolean isRead() {
    return this.read;
  }

  public boolean getRead() {
    return this.read;
  }

  public void setRead(boolean read) {
    this.read = read;
  }

  public boolean isLocationRequested() {
    return this.locationRequested;
  }

  public boolean getLocationRequested() {
    return this.locationRequested;
  }

  public void setLocationRequested(boolean locationRequested) {
    this.locationRequested = locationRequested;
  }

  public boolean isTimeRequested() {
    return this.timeRequested;
  }

  public boolean getTimeRequested() {
    return this.timeRequested;
  }

  public void setTimeRequested(boolean timeRequested) {
    this.timeRequested = timeRequested;
  }

  public String getToolName() {
    return this.toolName;
  }

  public void setToolName(String toolName) {
    this.toolName = toolName;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getToolPicUrl() {
    return this.toolPicUrl;
  }

  public void setToolPicUrl(String toolPicUrl) {
    this.toolPicUrl = toolPicUrl;
  }

  public boolean isIsRead() {
    return this.read;
  }

  // Getters and setters

  public Long getId() {
    return id;
  }

  public Long getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(Long receiverId) {
    this.receiverId = receiverId;
  }

  public String getReceiverName() {
    return receiverName;
  }

  public void setReceiverName(String receiverName) {
    this.receiverName = receiverName;
  }

  public Long getSenderId() {
    return senderId;
  }

  public void setSenderId(Long senderId) {
    this.senderId = senderId;
  }

  public String getSenderName() {
    return senderName;
  }

  public void setSenderName(String senderName) {
    this.senderName = senderName;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public NotificationType getType() {
    return type;
  }

  public void setType(NotificationType type) {
    this.type = type;
  }

  public boolean getIsRead() {
    return read;
  }

  public void setIsRead(boolean read) {
    this.read = read;
  }

  public Long getRelatedId() {
    return relatedId;
  }

  public void setRelatedId(Long relatedId) {
    this.relatedId = relatedId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return "{" + " id='" + getId() + "'" + ", receiverId='" + getReceiverId() + "'"
        + ", receiverName='" + getReceiverName() + "'" + ", senderId='" + getSenderId() + "'"
        + ", senderName='" + getSenderName() + "'" + ", message='" + getMessage() + "'"
        + ", toolPicUrl='" + getToolPicUrl() + "'" + ", toolName='" + getToolName() + "'"
        + ", address='" + getAddress() + "'" + ", notes='" + getNotes() + "'" + ", latitude='"
        + getLatitude() + "'" + ", longitude='" + getLongitude() + "'" + ", locationRequested='"
        + isLocationRequested() + "'" + ", timeRequested='" + isTimeRequested() + "'" + ", type='"
        + getType() + "'" + ", read='" + isRead() + "'" + ", relatedId='" + getRelatedId() + "'"
        + ", createdAt='" + getCreatedAt() + "'" + "}";
  }

}
