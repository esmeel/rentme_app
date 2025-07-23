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
import com.rentme.service.NotificationService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/location")
public class LocationController {

  @Autowired
  private NotificationService notificationService;

  @Autowired
  private UserRepository userRepository;

  @PostMapping("/send")
  @Transactional
  public ResponseEntity<?> sendLocation(@RequestBody LocationSendRequestDTO dto) {
    User sender = userRepository.findById(dto.getSenderId()).orElse(null);

    if (sender == null)
      return ResponseEntity.badRequest().body("Invalid user IDs.");

    if (dto.getIsDefault()) {
      sender.setDefaultLatitude(dto.getLatitude());
      sender.setDefaultLongitude(dto.getLongitude());
      sender.setDefaultAddress(dto.getAddress());
      userRepository.save(sender);
    }

    /*
     * Notification notif = new Notification(); notif.setSenderId(dto.getSenderId());
     * notif.setReceiverId(dto.getReceiverId()); notif.setMessage(message);
     * notif.setType(NotificationType.LOCATION_SENT); notif.setRelatedId(dto.getRentalId());
     * notif.setCreatedAt(LocalDateTime.now()); notif.setIsRead(false);
     * notif.setLatitude(dto.getLatitude()); notif.setLongitude(dto.getLongitude());
     * notif.setAddress(dto.getAddress()); notif.setSenderName(sender.getName());
     * notif.setReceiverName(receiver.getName()); notif.setToolPicUrl(message);
     * notif.setNotes(dto.getNotes()); notif.setMeeting(dto.getMeeting());
     * notif.setStarts(dto.getStarts()); notif.setEnds(dto.getEnds());
     * notif.setToolPicUrl(dto.getToolPic()); notif.setToolName(dto.getToolName());
     * notificationRepository.save(notif);
     */

    notificationService.sendNotification(dto);

    return ResponseEntity.ok("Location sent.");
  }
}
