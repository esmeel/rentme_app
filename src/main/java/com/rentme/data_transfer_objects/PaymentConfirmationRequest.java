package com.rentme.data_transfer_objects;

public class PaymentConfirmationRequest {
 private String nonce;
 private String amount;
 private Long rentalId;

 // Getters and setters
 public String getNonce() {
  return nonce;
 }

 public void setNonce(String nonce) {
  this.nonce = nonce;
 }

 public String getAmount() {
  return amount;
 }

 public void setAmount(double amount) {
  this.amount = "" + amount;
 }

 public Long getRentalId() {
  return rentalId;
 }

 public void setRentalId(Long rentalId) {
  this.rentalId = rentalId;
 }
}
