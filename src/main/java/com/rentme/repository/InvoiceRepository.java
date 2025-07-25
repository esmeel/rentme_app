package com.rentme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentme.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
 List<Invoice> findByUserId(Long UserId);

 // يمكنك إضافة استعلامات مخصصة هنا لاحقًا إذا احتجت
}
