package com.rentme.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentme.data_transfer_objects.InvoiceResponseDTO;
import com.rentme.model.Invoice;
import com.rentme.model.User;
import com.rentme.repository.InvoiceRepository;
import com.rentme.repository.UserRepository;
import com.rentme.security.JwtUtil;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

 private final InvoiceRepository invoiceRepository;
 private final JwtUtil jwtService;
 private final UserRepository userRepository;

 public InvoiceController(InvoiceRepository invoiceRepository, JwtUtil jwtService,
   UserRepository userRepository) {
  this.invoiceRepository = invoiceRepository;
  this.jwtService = jwtService;
  this.userRepository = userRepository;
 }

 @GetMapping("/my")
 // public ResponseEntity<List<InvoiceResponseDTO>> getMyInvoices(
 public ResponseEntity<?> getMyInvoices(@RequestHeader("Authorization") String authHeader) {
  String token = authHeader.replace("Bearer ", "");
  String email = jwtService.extractUsername(token);
  User user = userRepository.findByEmail(email);
  if (user == null)
   return ResponseEntity.status(404).body("User not found.");


  List<Invoice> invoices = invoiceRepository.findByUserId(user.getId());
  List<InvoiceResponseDTO> dtos = invoices.stream().map(InvoiceResponseDTO::new).toList();

  return ResponseEntity.ok(dtos);
 }
}
