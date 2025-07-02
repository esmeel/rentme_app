package com.rentme.data_transfer_objects;

import java.time.LocalDate;

public class RentalRequest {
  private Long toolId;
  private Long renterId;
  private LocalDate startDate;
  private LocalDate endDate;


  public Long getToolId() {
    return this.toolId;
  }

  public void setToolId(Long toolId) {
    this.toolId = toolId;
  }

  public Long getRenterId() {
    return this.renterId;
  }

  public void setRenterId(Long renterId) {
    this.renterId = renterId;
  }

  public LocalDate getStartDate() {
    return this.startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return this.endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }



}
