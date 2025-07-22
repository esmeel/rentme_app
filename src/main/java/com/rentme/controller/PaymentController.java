package com.rentme.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.braintreegateway.BraintreeGateway;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

 private final BraintreeGateway gateway;

 public PaymentController(BraintreeGateway gateway) {
  this.gateway = gateway;
 }

 @GetMapping("/token")
 public ResponseEntity<String> generateToken() {
  String clientToken = gateway.clientToken().generate();
  return ResponseEntity.ok(clientToken);
 }
}
