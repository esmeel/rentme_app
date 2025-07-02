
// package com.rentme.controller;

// import com.rentme.model.ChatMessage;
// import com.rentme.model.ChatRoom;
// import com.rentme.model.User;
// import com.rentme.repository.ChatMessageRepository;
// import com.rentme.repository.ChatRoomRepository;
// import com.rentme.repository.UserRepository;
// import jakarta.transaction.Transactional;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.Optional;

// @RestController
// @RequestMapping("/api/chat-rooms")
// public class ChatController {

// private final ChatRoomRepository chatRoomRepository;
// private final ChatMessageRepository chatMessageRepository;
// private final UserRepository userRepository;

// public ChatController(ChatRoomRepository chatRoomRepository,
// ChatMessageRepository chatMessageRepository,
// UserRepository userRepository) {
// this.chatRoomRepository = chatRoomRepository;
// this.chatMessageRepository = chatMessageRepository;
// this.userRepository = userRepository;
// }

// @GetMapping("/{chatRoomId}/messages")
// public ResponseEntity<?> getMessages(@PathVariable Long chatRoomId) {
// List<ChatMessage> messages =
// chatMessageRepository.findByChatRoomIdOrderByTimestampAsc(chatRoomId);
// return ResponseEntity.ok(messages);
// }

// @PostMapping("/{chatRoomId}/messages")
// @Transactional
// public ResponseEntity<?> sendMessage(@PathVariable Long chatRoomId,
// @RequestBody ChatMessageRequest request) {
// Optional<ChatRoom> chatRoomOpt = chatRoomRepository.findById(chatRoomId);
// Optional<User> senderOpt = userRepository.findById(request.getSenderId());

// if (chatRoomOpt.isEmpty() || senderOpt.isEmpty()) {
// return ResponseEntity.badRequest().body("Chat room or sender not found");
// }

// ChatMessage message = new ChatMessage();
// message.setChatRoom(chatRoomOpt.get());
// message.setSender(senderOpt.get());
// message.setContent(request.getContent());
// message.setTimestamp(LocalDateTime.now());

// chatMessageRepository.save(message);
// return ResponseEntity.ok(message);
// }

// public static class ChatMessageRequest {
// private Long senderId;
// private String content;

// public Long getSenderId() {
// return senderId;
// }

// public void setSenderId(Long senderId) {
// this.senderId = senderId;
// }

// public String getContent() {
// return content;
// }

// public void setContent(String content) {
// this.content = content;
// }
// }
// }
