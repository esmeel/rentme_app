package com.rentme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentme.model.ContactUs;

@Repository
public interface ContactUsRepository extends JpaRepository<ContactUs, Long> {

}
