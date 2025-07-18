package com.rentme.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Report {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private Long rentalId;
 private String type;
 private String message;
 private Long reporterId;
 private Long reportedId;
 private LocalDateTime timestamp;

 public Report() {}

 public Report(Long rentalId, String type, String message, Long reporterId, Long reportedId,
   LocalDateTime timestamp) {
  this.rentalId = rentalId;
  this.type = type;
  this.message = message;
  this.reporterId = reporterId;
  this.reportedId = reportedId;
  this.timestamp = timestamp;
 }

 // Getters and Setters

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

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

 public Long getReporterId() {
  return reporterId;
 }

 public void setReporterId(Long reporterId) {
  this.reporterId = reporterId;
 }

 public Long getReportedId() {
  return reportedId;
 }

 public void setReportedId(Long reportedId) {
  this.reportedId = reportedId;
 }

 public LocalDateTime getTimestamp() {
  return timestamp;
 }

 public void setTimestamp(LocalDateTime timestamp) {
  this.timestamp = timestamp;
 }


 @Override
 public String toString() {
  return "{" + " id='" + getId() + "'" + ", rentalId='" + getRentalId() + "'" + ", type='"
    + getType() + "'" + ", message='" + getMessage() + "'" + ", reporterId='" + getReporterId()
    + "'" + ", reportedId='" + getReportedId() + "'" + ", timestamp='" + getTimestamp() + "'" + "}";
 }

}
