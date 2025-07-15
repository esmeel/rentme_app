package com.rentme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.rentme.model.ScheduleEntry;

import jakarta.transaction.Transactional;

public interface ScheduleEntryRepository extends JpaRepository<ScheduleEntry, Long> {

  List<ScheduleEntry> findByReceiverId(Long receiverId);

  List<ScheduleEntry> findByRentalId(Long rentalId);



  @Modifying
  @Transactional
  void deleteByRentalId(long rentalId);



  @Modifying
  @Transactional
  // @Query("DELETE FROM ScheduleEntry e WHERE e.rentalid = :rentalId AND e.id <>
  // :confirmedId")
  void deleteByRentalIdAndIdNot(@Param("rentalId") Long rentalId,
      @Param("confirmedId") Long confirmedId);



}
