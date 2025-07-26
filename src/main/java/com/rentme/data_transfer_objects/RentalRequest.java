package com.rentme.data_transfer_objects;

import java.time.LocalDate;
import java.util.Locale;

public class RentalRequest {
  private Long toolId;
  private Long renterId;
  private LocalDate startDate;
  private LocalDate endDate;
  private double totalPrice;
  Locale locale;

  public Locale getLocale() {
    return this.locale;
  }



  public double getTotalPrice() {
    return this.totalPrice;
  }

  public void setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
  }


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
