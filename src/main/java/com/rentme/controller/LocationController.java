package com.rentme.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentme.data_transfer_objects.LocationSendRequestDTO;
import com.rentme.model.Notification;
import com.rentme.model.NotificationType;
import com.rentme.model.User;
import com.rentme.repository.NotificationRepository;
import com.rentme.repository.UserRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/location")
public class LocationController {

  @Autowired
  private NotificationRepository notificationRepository;

  @Autowired
  private UserRepository userRepository;

  @PostMapping("/send")
  @Transactional
  public ResponseEntity<?> sendLocation(@RequestBody LocationSendRequestDTO dto) {
    User sender = userRepository.findById(dto.getSenderId()).orElse(null);
    User receiver = userRepository.findById(dto.getReceiverId()).orElse(null);

    if (sender == null || receiver == null) {
      return ResponseEntity.badRequest().body("Invalid user IDs.");
    }
    if (dto.getIsDefault()) {
      sender.setDefaultLatitude(dto.getLatitude());
      sender.setDefaultLongitude(dto.getLongitude());
      sender.setDefaultAddress(dto.getAddress());
      userRepository.save(sender);
    }
    String message = sender.getName() + " sent a pickup location:\n" + dto.getAddress();
    System.out.println("meeting at LOCATION_SENT" + dto.getMeeting());
    Notification notif = new Notification();
    notif.setSenderId(dto.getSenderId());
    notif.setReceiverId(dto.getReceiverId());
    notif.setMessage(message);
    notif.setType(NotificationType.LOCATION_SENT);
    notif.setRelatedId(null);
    notif.setCreatedAt(LocalDateTime.now());
    notif.setIsRead(false);
    notif.setLatitude(dto.getLatitude());
    notif.setLongitude(dto.getLongitude());
    notif.setAddress(dto.getAddress());
    notif.setSenderName(sender.getName());
    notif.setNotes(dto.getNotes());
    notif.setMeeting(dto.getMeeting());
    notif.setStarts(dto.getStarts());
    notif.setEnds(dto.getEnds());
    notificationRepository.save(notif);
    System.out.println("will delete notification with rental id = " + dto.getRentalId());
    notificationRepository.deleteByTypeAndRelatedId(NotificationType.OWNER_LOCATION_REQUEST,
        dto.getRentalId());

    return ResponseEntity.ok("Location sent.");
  }
}
