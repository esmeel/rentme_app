
package com.rentme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentme.model.Rental;
import com.rentme.model.RentalStatus;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByOwnerIdOrRenterId(Long ownerId, Long renterId);

    List<Rental> findByRenterId(Long renterId);

    List<Rental> findByToolId(Long toolId);

    List<Rental> findByOwnerId(Long toolId);

    List<Rental> findRentalByOwnerIdAndStatus(Long ownerId, RentalStatus status);

    Rental findRentalById(Long id);

    boolean existsByToolIdAndRenterId(Long toolId, Long renterId);

    boolean existsByToolIdAndRenterIdAndStatusNot(Long toolId, Long renterId, RentalStatus ended);

}
