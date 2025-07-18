package com.rentme.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentme.data_transfer_objects.ReportRequestDTO;
import com.rentme.service.ReportService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

 private final ReportService reportService;

 public ReportController(ReportService reportService) {
  this.reportService = reportService;
 }

 @PostMapping
 public ResponseEntity<String> report(@RequestBody ReportRequestDTO dto,
   HttpServletRequest request) {
  reportService.submitReport(dto, request);
  return ResponseEntity.ok("Report submitted successfully");
 }
}
