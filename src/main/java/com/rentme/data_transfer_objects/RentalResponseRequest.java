package com.rentme.data_transfer_objects;

public class RentalResponseRequest {
  private Long rentalId;
  private Long relatedId;
  private Long receiverId;
  private Long senderId;
  private java.time.LocalDate ends;
  private java.time.LocalDate starts;

  public java.time.LocalDate getEnds() {
    return this.ends;
  }

  public void setEnds(java.time.LocalDate ends) {
    this.ends = ends;
  }

  public java.time.LocalDate getStarts() {
    return this.starts;
  }

  public void setStarts(java.time.LocalDate starts) {
    this.starts = starts;
  }

  public Long getReceiverId() {
    return this.receiverId;
  }

  public void setReceiverId(Long receiverId) {
    this.receiverId = receiverId;
  }

  public Long getSenderId() {
    return this.senderId;
  }

  public void setSenderId(Long senderId) {
    this.senderId = senderId;
  }

  public Long getRelatedId() {
    return this.relatedId;
  }

  public void setRelatedId(Long relatedId) {
    this.relatedId = relatedId;
  }

  public boolean getAccept() {
    return this.accept;
  }

  private boolean accept;

  // getters and setters
  public Long getRentalId() {
    return rentalId;
  }

  public void setRentalId(Long rentalId) {
    this.rentalId = rentalId;
  }

  public boolean isAccept() {
    return accept;
  }

  public void setAccept(boolean accept) {
    this.accept = accept;
  }

  @Override
  public String toString() {
    return "{" + " rentalId='" + getRentalId() + "'" + ", relatedId='" + getRelatedId() + "'"
        + ", receiverId='" + getReceiverId() + "'" + ", senderId='" + getSenderId() + "'"
        + ", accept='" + isAccept() + "'" + "}";
  }

}
