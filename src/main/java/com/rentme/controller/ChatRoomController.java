
package com.rentme.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentme.controller.RequiredArgsConstructor;
import com.rentme.data_transfer_objects.ChatMessageDTO;
import com.rentme.data_transfer_objects.ChatRoomSummaryDTO;
import com.rentme.model.ChatMessage;
import com.rentme.model.ChatRoom;
import com.rentme.model.User;
import com.rentme.repository.ChatMessageRepository;
import com.rentme.repository.ChatRoomRepository;
import com.rentme.repository.UserRepository;
import com.rentme.service.ChatNotificationService;

@RestController
@RequestMapping("/api/chat-rooms")
public class ChatRoomController {
    public ChatRoomController() {
        System.err.println("in ChatRoomController()");

    }

    @Autowired
    private ChatRoomRepository chatRoomRepo;
    @Autowired
    private ChatMessageRepository chatMessageRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ChatNotificationService notificationService;

    @GetMapping("/{chatRoomId}/messages")
    public ResponseEntity<List<ChatMessageDTO>> getChatMessages(@PathVariable Long chatRoomId) {
        List<ChatMessage> messages = chatMessageRepo.findByChatRoomId(chatRoomId);
        List<ChatMessageDTO> dtos = messages.stream()
                .map(ChatMessageDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/my/{userId}")
    public ResponseEntity<List<ChatRoomSummaryDTO>> myChats(@PathVariable Long userId) {
        System.err.println("in myChats(..)");

        List<ChatRoom> rooms = chatRoomRepo.findByOwnerId(userId);
        rooms.addAll(chatRoomRepo.findByRenterId(userId));
        List<ChatRoomSummaryDTO> dtos = new ArrayList<>();
        for (ChatRoom room : rooms) {
            System.err.println("in room: " + room.getId());

            List<ChatMessage> msgs = chatMessageRepo.findByChatRoomIdOrderByTimestampAsc(room.getId());
            String lastContent = msgs.isEmpty() ? "" : msgs.get(msgs.size() - 1).getContent();
            LocalDateTime lastTime = msgs.isEmpty() ? null : msgs.get(msgs.size() - 1).getTimestamp();
            String name = Objects.equals(room.getOwner().getId(), userId)
                    ? room.getRenter().getName()
                    : room.getOwner().getName();

            dtos.add(new ChatRoomSummaryDTO(room.getId(), name, lastContent, lastTime, room.isClosed()));
        }

        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/{chatRoomId}/messages")
    public ResponseEntity<?> sendMessage(@PathVariable Long chatRoomId, @RequestBody ChatMessageRequest req) {
        ChatRoom room = chatRoomRepo.findById(chatRoomId).orElseThrow();
        if (room.isClosed())
            return ResponseEntity.status(403).body("Chat closed");

        User sender = userRepo.findById(req.getSenderId()).orElseThrow();
        ChatMessage msg = new ChatMessage();
        msg.setChatRoom(room);
        msg.setSender(sender);
        msg.setContent(req.getContent());
        msg.setTimestamp(LocalDateTime.now());
        chatMessageRepo.save(msg);
        System.out.println("msg " + msg + " will be sent from " + sender.getName() + "to room " + room.getId() + ".");
        long count = chatMessageRepo.countByChatRoomId(chatRoomId);
        boolean firstMsg = (count == 1);
        if (firstMsg) {
            Long receiverId = Objects.equals(sender.getId(), room.getOwner().getId())
                    ? room.getRenter().getId()
                    : room.getOwner().getId();
            System.out.println("notification for first message will be sent now.");
            notificationService.notifyIfFirstMessage(msg);

        }

        return ResponseEntity.ok(msg);
    }

    public static class ChatMessageRequest {
        private Long senderId;
        private String content;

        public Long getSenderId() {
            return this.senderId;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String contene) {
            this.content = contene;
        }

        public void setSenderId(long senderId) {
            this.senderId = senderId;
        }

    }
}
