package com.rentme.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentme.model.IdentityRequest;
import com.rentme.model.User;

public interface IdentityRequestRepository extends JpaRepository<IdentityRequest, Long> {

  List<IdentityRequest> findByStatus(String status); // e.g., "PENDING"

  Optional<IdentityRequest> findTopByUserOrderBySubmittedAtDesc(User user);

}
