package com.rentme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentme.model.Tool;;

public interface ToolRepository extends JpaRepository<Tool, Long> {

  List<Tool> findByOwnerId(Long ownerId);

  List<Tool> findByOwnerIdAndAvailableFalse(Long ownerId);

}


