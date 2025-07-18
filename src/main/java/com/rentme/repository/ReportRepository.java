package com.rentme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentme.model.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
