
package com.rentme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentme.model.Rental;
import com.rentme.model.RentalStatus;
import com.rentme.model.User;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByRenterId(Long renterId);

    List<Rental> findByToolId(Long toolId);

    List<Rental> findByOwnerId(Long toolId);

    List<Rental> findRentalByOwnerAndStatus(User owner, RentalStatus status);

    boolean existsByToolIdAndRenterId(Long toolId, Long renterId);

}
