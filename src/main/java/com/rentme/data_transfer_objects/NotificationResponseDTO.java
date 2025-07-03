package com.rentme.data_transfer_objects;

import java.time.LocalDateTime;

import com.rentme.model.Notification;

public class NotificationResponseDTO {

  private Long id;
  private String type;
  private String message;
  private boolean read;
  private Long relatedId;

  private Long senderId;
  private String senderName;

  private Long receiverId;
  private String receiverName;

  private LocalDateTime createdAt;

  public NotificationResponseDTO(Notification notification) {
    this.id = notification.getId();
    this.type = notification.getType().name();
    this.message = notification.getMessage();
    this.read = notification.getIsRead();
    this.relatedId = notification.getRelatedId();
    this.senderId = notification.getSenderId();
    this.senderName = notification.getSenderName();
    this.receiverId = notification.getReceiverId();
    this.receiverName = notification.getReceiverName();
    this.createdAt = notification.getCreatedAt();
  }

  // Getters only for brevity, add setters if needed

  public Long getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  public String getMessage() {
    return message;
  }

  public boolean isRead() {
    return read;
  }

  public Long getRelatedId() {
    return relatedId;
  }

  public Long getSenderId() {
    return senderId;
  }

  public String getSenderName() {
    return senderName;
  }

  public Long getReceiverId() {
    return receiverId;
  }

  public String getReceiverName() {
    return receiverName;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", type='" + getType() + "'" +
        ", message='" + getMessage() + "'" +
        ", read='" + isRead() + "'" +
        ", relatedId='" + getRelatedId() + "'" +
        ", senderId='" + getSenderId() + "'" +
        ", senderName='" + getSenderName() + "'" +
        ", receiverId='" + getReceiverId() + "'" +
        ", receiverName='" + getReceiverName() + "'" +
        ", createdAt='" + getCreatedAt() + "'" +
        "}";
  }

}