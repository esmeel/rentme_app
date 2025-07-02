package com.rentme.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentme.model.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
  Optional<ChatRoom> findByRentalId(Long rentalId);

  List<ChatRoom> findByOwnerId(Long ownerId);

  List<ChatRoom> findByRenterId(Long renterId);

  long countByChatRoomId(Long chatRoomId);

}
