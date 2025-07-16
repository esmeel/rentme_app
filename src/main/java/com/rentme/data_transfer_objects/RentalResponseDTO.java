package com.rentme.data_transfer_objects;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.rentme.model.Rental;
import com.rentme.model.RentalStatus;

public class RentalResponseDTO {
  private Long id;
  private LocalDate startDate;
  private LocalDate endDate;
  private LocalDateTime createdAt;
  private RentalStatus status;
  private Long toolId;
  private Long ownerId;
  private Long renterId;
  private String toolName;
  private String toolPic;
  private String ownerName;
  private String renterName;
  private boolean accepted;

  public RentalResponseDTO(Rental rental) {
    this.id = rental.getId();
    this.status = rental.getStatus();
    this.startDate = rental.getStartDate();
    this.endDate = rental.getEndDate();
    this.toolName = rental.getToolName();
    this.toolPic = rental.getToolPic();
    this.toolId = rental.getTool().getId();
    this.ownerId = rental.getOwnerId();
    this.renterId = rental.getRenterId();
    this.ownerName = rental.getOwnerName();
    this.renterName = rental.getRenterName();
    this.accepted = rental.isAccepted();
  }

  public String getToolName() {
    return this.toolName;
  }

  public void setToolName(String toolName) {
    this.toolName = toolName;
  }

  public String getToolPic() {
    return this.toolPic;
  }

  public void setToolPic(String toolPic) {
    this.toolPic = toolPic;
  }

  public String getOwnerName() {
    return this.ownerName;
  }

  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

  public String getRenterName() {
    return this.renterName;
  }

  public void setRenterName(String renterName) {
    this.renterName = renterName;
  }

  public boolean isAccepted() {
    return this.accepted;
  }

  public boolean getAccepted() {
    return this.accepted;
  }

  public void setAccepted(boolean accepted) {
    this.accepted = accepted;
  }


  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }


  public RentalResponseDTO(Long id, RentalStatus status, LocalDate startDate, LocalDate endDate,
      String toolName, String toolPic, Long toolId, Long ownerId, Long renterId, String ownerName,
      String renterName, boolean accepted) {
    this.id = id;
    this.status = status;
    this.startDate = startDate;
    this.endDate = endDate;
    this.toolName = toolName;
    this.toolPic = toolPic;
    this.toolId = toolId;
    this.ownerId = ownerId;
    this.renterId = renterId;
    this.ownerName = ownerName;
    this.renterName = renterName;
    this.accepted = accepted;
  }


  // Getters and setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
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
    return "{" + "id=" + id + ", startDate='" + startDate + '\'' + ", endDate='" + endDate + '\''
        + ", status='" + status + '\'' + ", toolId=" + toolId + ", ownerId=" + ownerId
        + ", renterId=" + renterId + '}';
  }
}
