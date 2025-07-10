package com.rentme.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentme.data_transfer_objects.LocationOrTimeRequestDTO;
import com.rentme.model.Notification;
import com.rentme.model.NotificationType;
import com.rentme.model.User;
import com.rentme.repository.NotificationRepository;
import com.rentme.repository.UserRepository;

@Service
public class NotificationService {

  @Autowired
  private NotificationRepository notificationRepo;
  private final NotificationRepository notificationRepository;
  private final UserRepository userRepository;

  public NotificationService(NotificationRepository notificationRepository,
      UserRepository userRepository) {
    this.notificationRepository = notificationRepository;
    this.userRepository = userRepository;
  }

  public void sendNotification(Long receiverId, Long senderId, NotificationType type,
      String message, Long rentalId) {
    User receiver = userRepository.findById(receiverId)
        .orElseThrow(() -> new RuntimeException("Receiver user not found"));

    User sender = userRepository.findById(senderId).orElse(null);

    Notification notification = new Notification();
    notification.setReceiverId(receiver.getId());
    notification.setSenderId(sender.getId());
    notification.setRelatedId(rentalId);
    notification.setType(type);
    notification.setMessage(sender.getName() + " says: " + message);
    notification.setIsRead(false);
    notification.setSenderName(sender.getName());
    notification.setReceiverName(receiver.getName());
    notification.setCreatedAt(LocalDateTime.now());
    if (type == NotificationType.FINAL_SCHEDULE_CONFIRMED)
      notification.setMeeting(message);

    System.err.println(" Notification to save: " + notification.toString());

    notificationRepository.save(notification);
    System.out.println("🔔 Notification saved for user " + receiver.getEmail());

  }

  public void sendNotification(Notification notification) {


    System.err.println(" Notification to save: " + notification.toString());

    notificationRepository.save(notification);

  }

  public void deleteByTypeAndRental(NotificationType type, Long rentalId) {
    List<Notification> notifs = notificationRepository.findByTypeAndRelatedId(type, rentalId);
    if (notifs.isEmpty())
      return;

    notificationRepository.deleteAll(notifs);
  }

  public void sendLocationRequest(LocationOrTimeRequestDTO dto) {
    System.out.println("dto.getMeeting()==" + dto.getMeeting());
    Notification notif = new Notification();
    notif.setSenderId(dto.getSenderId());
    notif.setReceiverId(dto.getReceiverId());
    notif.setRelatedId(dto.getRentalId());
    notif.setType(NotificationType.OWNER_LOCATION_REQUEST);
    notif.setMessage(dto.getSenderNamne() + " is requesting the pickup location");
    notif.setCreatedAt(LocalDateTime.now());
    notif.setIsRead(false);

    notif.setMeeting(dto.getMeeting());

    notif.setToolPicUrl(dto.getToolPicUrl());

    notificationRepo.save(notif);
  }

  public void sendTimeRequest(LocationOrTimeRequestDTO dto) {

    Notification notif = new Notification();
    notif.setSenderId(dto.getSenderId());
    notif.setReceiverId(dto.getReceiverId());
    notif.setRelatedId(dto.getRentalId());
    notif.setType(NotificationType.OWNER_TIME_REQUEST);
    notif.setMessage(dto.getSenderNamne() + " is requesting a pickup schedule.");
    notif.setCreatedAt(LocalDateTime.now());
    notif.setIsRead(false);
    notif.setToolPicUrl(dto.getToolPicUrl());
    notificationRepo.save(notif);
  }

  public List<Notification> getUserNotifications(User user) {
    var notifList = notificationRepository.findByReceiverIdOrderByCreatedAtDesc(user.getId());

    return notifList;
  }

  public void markAllAsRead(Long userId) {
    List<Notification> notifs = notificationRepository.findByReceiverId(userId);
    if (notifs.isEmpty())
      return;
    for (Notification n : notifs) {
      n.setIsRead(true);
    }
    notificationRepository.saveAll(notifs);
  }

}
