package com.rentme.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.rentme.model.ChatMessage;
import com.rentme.model.ChatRoom;
import com.rentme.model.Notification;
import com.rentme.model.NotificationType;
import com.rentme.model.User;
import com.rentme.repository.ChatMessageRepository;
import com.rentme.repository.ChatRoomRepository;
import com.rentme.repository.NotificationRepository;
import com.rentme.repository.UserRepository;

@Service
public class ChatNotificationService {

    private final ChatMessageRepository messageRepo;
    private final NotificationRepository notificationRepo;

    public ChatNotificationService(ChatMessageRepository messageRepo, NotificationRepository notificationRepo,
            ChatRoomRepository roomRepo, UserRepository userRepo) {
        this.messageRepo = messageRepo;
        this.notificationRepo = notificationRepo;
    }

    public void notifyIfFirstMessage(ChatMessage message) {
        Long roomId = message.getChatRoom().getId();
        Long senderId = message.getSender().getId();

        long count = messageRepo.countByChatRoomId(roomId);
        if (count == 1) {
            ChatRoom room = message.getChatRoom();
            User receiver = room.getRenter().getId().equals(senderId) ? room.getOwner() : room.getRenter();

            Notification notif = new Notification();
            notif.setReceiverId(receiver.getId());
            notif.setMessage("New Message");
            notif.setRelatedId(roomId);
            notif.setType(NotificationType.CHAT_MESSAGE);
            notif.setCreatedAt(LocalDateTime.now());
            notif.setIsRead(false);

            notificationRepo.save(notif);
        }
    }
}
