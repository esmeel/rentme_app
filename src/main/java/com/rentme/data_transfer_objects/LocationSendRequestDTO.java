package com.rentme.data_transfer_objects;

public class LocationSendRequestDTO {
  private Long senderId;
  private Long receiverId;
  private double latitude;
  private double longitude;
  private String address;
  private String notes;

  private boolean isDefault;

  public String getNotes() {
    return this.notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public Long getSenderId() {
    return this.senderId;
  }

  public void setSenderId(Long senderId) {
    this.senderId = senderId;
  }

  public Long getReceiverId() {
    return this.receiverId;
  }

  public void setReceiverId(Long receiverId) {
    this.receiverId = receiverId;
  }

  public double getLatitude() {
    return this.latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return this.longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public boolean isIsDefault() {
    return this.isDefault;
  }

  public boolean getIsDefault() {
    return this.isDefault;
  }

  public void setIsDefault(boolean isDefault) {
    this.isDefault = isDefault;
  }

  // Getters and Setters
}
