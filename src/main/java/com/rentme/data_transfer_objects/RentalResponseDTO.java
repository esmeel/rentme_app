package com.rentme.data_transfer_objects;

import com.rentme.model.Rental;
import com.rentme.model.RentalStatus;
import com.rentme.service.RentalService;

public class RentalResponseDTO {
  private Long id;
  private String startDate;
  private String endDate;
  private RentalStatus status;
  private Long toolId;
  private Long ownerId;
  private Long renterId;

  public RentalResponseDTO(Rental rental) {
    this.id = rental.getId();
    this.startDate = rental.getStartDate().toString();
    this.endDate = rental.getEndDate().toString();
    this.status = rental.getStatus();
    this.toolId = rental.getTool().getId();
    this.ownerId = rental.getOwner().getId();
    this.renterId = rental.getRenter().getId();
  }

  // Getters and setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public RentalStatus getStatus() {
    return status;
  }

  public void setStatus(RentalStatus status) {
    this.status = status;
  }

  public Long getToolId() {
    return toolId;
  }

  public void setToolId(Long toolId) {
    this.toolId = toolId;
  }

  public Long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Long ownerId) {
    this.ownerId = ownerId;
  }

  public Long getRenterId() {
    return renterId;
  }

  public void setRenterId(Long renterId) {
    this.renterId = renterId;
  }

  @Override
  public String toString() {
    return "{" +
        "id=" + id +
        ", startDate='" + startDate + '\'' +
        ", endDate='" + endDate + '\'' +
        ", status='" + status + '\'' +
        ", toolId=" + toolId +
        ", ownerId=" + ownerId +
        ", renterId=" + renterId +
        '}';
  }
}
