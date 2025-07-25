package com.rentme.data_transfer_objects;

import java.time.LocalDate;

import com.rentme.model.Invoice;

public class InvoiceResponseDTO {
 private String invoiceId;
 private String toolName;
 private String toolPicUrl;
 private LocalDate startDate;
 private LocalDate endDate;
 private double totalPrice;

 public InvoiceResponseDTO(Invoice invoice) {
  this.invoiceId = invoice.getId().toString();
  this.toolName = invoice.getToolName();
  this.toolPicUrl = invoice.getToolPicUrl();
  this.startDate = invoice.getStartDate();
  this.endDate = invoice.getEndDate();
  this.totalPrice = invoice.getTotalPrice();
 }

 public String getInvoiceId() {
  return this.invoiceId;
 }


 public String getToolName() {
  return this.toolName;
 }



 public String getToolPicUrl() {
  return this.toolPicUrl;
 }


 public LocalDate getStartDate() {
  return this.startDate;
 }


 public LocalDate getEndDate() {
  return this.endDate;
 }



 public double getTotalPrice() {
  return this.totalPrice;
 }



}
