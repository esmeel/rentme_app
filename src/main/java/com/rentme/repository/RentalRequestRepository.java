package com.rentme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentme.model.RentalRequest;
import com.rentme.model.User;

@Repository
public interface RentalRequestRepository extends JpaRepository<RentalRequest, Long> {
  List<RentalRequest> findByOwner(User owner);

  List<RentalRequest> findByRenter(User renter);
}
