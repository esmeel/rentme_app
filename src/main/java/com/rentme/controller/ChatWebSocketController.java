package com.rentme.controller;

import java.time.LocalDateTime;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.rentme.data_transfer_objects.ChatMessageDTO;
import com.rentme.model.ChatMessage;
import com.rentme.model.ChatRoom;
import com.rentme.model.User;
import com.rentme.repository.ChatMessageRepository;
import com.rentme.repository.ChatRoomRepository;
import com.rentme.repository.UserRepository;

@Controller
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    public ChatWebSocketController(SimpMessagingTemplate messagingTemplate,
            ChatRoomRepository chatRoomRepository,
            ChatMessageRepository chatMessageRepository,
            UserRepository userRepository) {
        this.messagingTemplate = messagingTemplate;
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.userRepository = userRepository;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessageDTO messageDTO) {
        ChatRoom chatRoom = chatRoomRepository.findById(messageDTO.getChatRoomId()).orElseThrow();
        User sender = userRepository.findById(messageDTO.getSenderId()).orElseThrow();

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setChatRoom(chatRoom);
        chatMessage.setSender(sender);
        chatMessage.setContent(messageDTO.getContent());
        chatMessage.setTimestamp(LocalDateTime.now());

        chatMessageRepository.save(chatMessage);

        messagingTemplate.convertAndSend("/topic/chat/" + chatRoom.getId(), messageDTO);
    }
}
