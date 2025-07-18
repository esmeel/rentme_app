package com.rentme.data_transfer_objects;

public class ReportRequestDTO {

 private Long rentalId;
 private String type;
 private String message;
 private Long reportedId;
 private Long reporterId;

 public Long getReporterId() {
  return this.reporterId;
 }

 public void setReporterId(Long reporterId) {
  this.reporterId = reporterId;
 }

 public ReportRequestDTO() {}

 public Long getRentalId() {
  return rentalId;
 }

 public void setRentalId(Long rentalId) {
  this.rentalId = rentalId;
 }

 public String getType() {
  return type;
 }

 public void setType(String type) {
  this.type = type;
 }

 public String getMessage() {
  return message;
 }

 public void setMessage(String message) {
  this.message = message;
 }

 public Long getReportedId() {
  return reportedId;
 }

 public void setReportedId(Long reportedId) {
  this.reportedId = reportedId;
 }


 @Override
 public String toString() {
  return "{" + " rentalId='" + getRentalId() + "'" + ", type='" + getType() + "'" + ", message='"
    + getMessage() + "'" + ", reportedId='" + getReportedId() + "'" + "}";
 }


}
