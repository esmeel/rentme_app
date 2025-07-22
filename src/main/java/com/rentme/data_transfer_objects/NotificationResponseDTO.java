package com.rentme.data_transfer_objects;

import java.time.LocalDateTime;

import com.rentme.model.Notification;

public class NotificationResponseDTO {

  private Long id;
  private Long toolId;

  public void setId(Long id) {
    this.id = id;
  }

  public Long getToolId() {
    return this.toolId;
  }

  public void setToolId(Long toolId) {
    this.toolId = toolId;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean getRead() {
    return this.read;
  }

  public void setRead(boolean read) {
    this.read = read;
  }

  public void setRelatedId(Long relatedId) {
    this.relatedId = relatedId;
  }

  public void setSenderId(Long senderId) {
    this.senderId = senderId;
  }

  public void setSenderName(String senderName) {
    this.senderName = senderName;
  }

  public void setReceiverId(Long receiverId) {
    this.receiverId = receiverId;
  }

  public void setReceiverName(String receiverName) {
    this.receiverName = receiverName;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

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
    return "{" + " id='" + getId() + "'" + ", type='" + getType() + "'" + ", message='"
        + getMessage() + "'" + ", read='" + isRead() + "'" + ", relatedId='" + getRelatedId() + "'"
        + ", senderId='" + getSenderId() + "'" + ", senderName='" + getSenderName() + "'"
        + ", receiverId='" + getReceiverId() + "'" + ", receiverName='" + getReceiverName() + "'"
        + ", createdAt='" + getCreatedAt() + "'" + "}";
  }

}
