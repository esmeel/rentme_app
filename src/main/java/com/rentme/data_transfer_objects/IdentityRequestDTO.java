package com.rentme.data_transfer_objects;

import java.util.List;

public class IdentityRequestDTO {
  private Long id;
  private String name;
  private String documentType;
  private List<String> imageUrls; // URLs for the uploaded images
  private String status;

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDocumentType() {
    return documentType;
  }

  public void setDocumentType(String documentType) {
    this.documentType = documentType;
  }

  public List<String> getImageUrls() {
    return imageUrls;
  }

  public void setImageUrls(List<String> imageUrls) {
    this.imageUrls = imageUrls;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
