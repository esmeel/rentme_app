package com.rentme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentme.model.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
  List<ChatMessage> findByChatRoomIdOrderByTimestampAsc(Long chatRoomId);

  long countByChatRoomId(Long chatRoomId);

  List<ChatMessage> findByChatRoomId(Long chatRoomId);

}
