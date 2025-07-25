package com.rentme.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Invoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long userId;

  public Long getUserId() {
    return this.userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }


  private String invoiceNumber;

  private String toolName;
  private String toolPicUrl;

  private String renterName;
  private Long renterId;

  private LocalDate startDate;
  private LocalDate endDate;

  private int days;
  private double pricePerDay;
  private double subtotal;
  private double serviceFee;
  private double totalPrice;

  private LocalDate createdAt = LocalDate.now();



  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getInvoiceNumber() {
    return this.invoiceNumber;
  }

  public void setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }

  public String getToolName() {
    return this.toolName;
  }

  public void setToolName(String toolName) {
    this.toolName = toolName;
  }

  public String getToolPicUrl() {
    return this.toolPicUrl;
  }

  public void setToolPicUrl(String toolPicUrl) {
    this.toolPicUrl = toolPicUrl;
  }

  public String getRenterName() {
    return this.renterName;
  }

  public void setRenterName(String renterName) {
    this.renterName = renterName;
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

  public int getDays() {
    return this.days;
  }

  public void setDays(int days) {
    this.days = days;
  }

  public double getPricePerDay() {
    return this.pricePerDay;
  }

  public void setPricePerDay(double pricePerDay) {
    this.pricePerDay = pricePerDay;
  }

  public double getSubtotal() {
    return this.subtotal;
  }

  public void setSubtotal(double subtotal) {
    this.subtotal = subtotal;
  }

  public double getServiceFee() {
    return this.serviceFee;
  }

  public void setServiceFee(double serviceFee) {
    this.serviceFee = serviceFee;
  }

  public double getTotalPrice() {
    return this.totalPrice;
  }

  public void setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public LocalDate getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }


  @Override
  public String toString() {
    return "{" + " id='" + getId() + "'" + ", invoiceNumber='" + getInvoiceNumber() + "'"
        + ", toolName='" + getToolName() + "'" + ", toolPicUrl='" + getToolPicUrl() + "'"
        + ", renterName='" + getRenterName() + "'" + ", renterId='" + getRenterId() + "'"
        + ", startDate='" + getStartDate() + "'" + ", endDate='" + getEndDate() + "'" + ", days='"
        + getDays() + "'" + ", pricePerDay='" + getPricePerDay() + "'" + ", subtotal='"
        + getSubtotal() + "'" + ", serviceFee='" + getServiceFee() + "'" + ", totalPrice='"
        + getTotalPrice() + "'" + ", createdAt='" + getCreatedAt() + "'" + "}";
  }

}
