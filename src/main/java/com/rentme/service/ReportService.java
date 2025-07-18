package com.rentme.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.rentme.data_transfer_objects.ReportRequestDTO;
import com.rentme.model.Report;
import com.rentme.repository.ReportRepository;
import com.rentme.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ReportService {

 private final ReportRepository reportRepository;
 private final JwtUtil jwtUtil;

 public ReportService(ReportRepository reportRepository, JwtUtil jwtUtil) {
  this.reportRepository = reportRepository;
  this.jwtUtil = jwtUtil;
 }

 public void submitReport(ReportRequestDTO dto, HttpServletRequest request) {


  Report report = new Report(dto.getRentalId(), dto.getType(), dto.getMessage(),
    dto.getReporterId(), dto.getReportedId(), LocalDateTime.now());

  reportRepository.save(report);
 }
}
