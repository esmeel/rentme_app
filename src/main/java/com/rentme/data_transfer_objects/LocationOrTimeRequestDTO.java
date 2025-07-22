package com.rentme.data_transfer_objects;

public class LocationOrTimeRequestDTO {
  private Long rentalId;
  private Long senderId;
  private Long receiverId;
  private String senderNamne;
  private String receiverName;
  private String nofitiType;

  public String getNofitiType() {
    return this.nofitiType;
  }

  public void setNofitiType(String nofitiType) {
    this.nofitiType = nofitiType;
  }

  private String toolPicUrl;

  private String meeting;

  private java.time.LocalDate starts;
  private java.time.LocalDate ends;

  public String getReceiverName() {
    return this.receiverName;
  }

  public void setReceiverName(String receiverName) {
    this.receiverName = receiverName;
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

  public String getMeeting() {
    return this.meeting;
  }

  public void setMeeting(String meeting) {
    this.meeting = meeting;
  }

  public String getToolPicUrl() {
    return this.toolPicUrl;
  }

  public void setToolPicUrl(String toolPicUrl) {
    this.toolPicUrl = toolPicUrl;
  }

  public String getSenderNamne() {
    return this.senderNamne;
  }

  public void setSenderNamne(String senderNamne) {
    this.senderNamne = senderNamne;
  }

  // Getters and setters
  public Long getRentalId() {
    return rentalId;
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

  @Override
  public String toString() {
    return "{" + " rentalId='" + getRentalId() + "'" + ", senderId='" + getSenderId() + "'"
        + ", receiverId='" + getReceiverId() + "'" + ", senderNamne='" + getSenderNamne() + "'"
        + "}";
  }
}
