package com.rentme.data_transfer_objects;

public class LocationOrTimeRequestDTO {
  private Long rentalId;
  private Long senderId;
  private Long receiverId;
  private String senderNamne;
  private String toolPicUrl;

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
    return "{" +
        " rentalId='" + getRentalId() + "'" +
        ", senderId='" + getSenderId() + "'" +
        ", receiverId='" + getReceiverId() + "'" +
        ", senderNamne='" + getSenderNamne() + "'" +
        "}";
  }
}
