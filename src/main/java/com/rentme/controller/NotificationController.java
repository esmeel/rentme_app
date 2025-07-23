package com.rentme.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentme.data_transfer_objects.LocationOrTimeRequestDTO;
import com.rentme.data_transfer_objects.NotificationResponseDTO;
import com.rentme.model.Notification;
import com.rentme.model.NotificationType;
import com.rentme.model.User;
import com.rentme.repository.NotificationRepository;
import com.rentme.repository.UserRepository;
import com.rentme.security.JwtUtil;
import com.rentme.service.NotificationService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

  private final NotificationService notificationService;
  private final NotificationRepository notificationRepository;
  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;

  public NotificationController(NotificationService notificationService,
      NotificationRepository notificationRepository, UserRepository userRepository,
      JwtUtil jwtUtil) {

    this.notificationService = notificationService;
    this.notificationRepository = notificationRepository;
    this.userRepository = userRepository;
    this.jwtUtil = jwtUtil;

    System.out.println("We are in: NotificationController");
  }

  @GetMapping
  public List<Notification> getMyNotifications(HttpServletRequest request) {
    String token = jwtUtil.extractTokenFromRequest(request);
    String email = jwtUtil.extractEmail(token);
    User user = userRepository.findByEmail(email);
    if (user == null) {
      System.err.println("User not found!");
      return new ArrayList<>();
    }
    return notificationService.getUserNotifications(user);
  }

  @PutMapping("/{id}/read")
  public ResponseEntity<?> markAsRead(@PathVariable Long id) {
    Notification notif = notificationRepository.findById(id).orElseThrow();
    notif.setIsRead(true);
    notificationRepository.save(notif);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/notification/by-type")
  public ResponseEntity<?> deleteByTypeAndRental(@RequestParam NotificationType type,
      @RequestParam Long rentalId) {
    notificationService.deleteByTypeAndRental(type, rentalId);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/send")
  public ResponseEntity<?> sendNotification(@RequestBody NotificationResponseDTO requestDto,
      HttpServletRequest request) {
    System.out.println(requestDto);
    try {
      String token = jwtUtil.extractTokenFromRequest(request);
      String senderEmail = jwtUtil.extractEmail(token);
      User sender = userRepository.findByEmail(senderEmail);
      User receiver = userRepository.findById(requestDto.getReceiverId()).orElse(null);

      if (receiver == null || sender == null) {
        return ResponseEntity.badRequest().body("User not found");
      }

      Notification notification = new Notification();
      notification.setSenderId(sender.getId());
      notification.setRelatedId(sender.getId());
      notification.setReceiverId(receiver.getId());
      notification.setMessage(requestDto.getMessage());
      notification.setType(NotificationType.valueOf(requestDto.getType()));
      notification.setIsRead(requestDto.isRead());
      notificationRepository.save(notification);

      return ResponseEntity.ok("Notification sent");
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteNotification(@PathVariable Long id) {
    if (!notificationRepository.existsById(id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found");
    }

    notificationRepository.deleteById(id);
    System.out.println("We will delete notification with id=" + id);

    return ResponseEntity.ok("Notification deleted");
  }

  @PostMapping("/request-location")
  public ResponseEntity<?> requestLocation(@RequestBody LocationOrTimeRequestDTO dto) {
    System.out.println("🔔 Received DTO: " + dto);

    notificationService.sendNotification(dto);


    return ResponseEntity.ok("Location request sent");
  }

  @PostMapping("/request-time")
  public ResponseEntity<?> requestTime(@RequestBody LocationOrTimeRequestDTO dto) {
    System.out.println("🔔 Received DTO: " + dto);

    notificationService.sendNotification(dto);


    notificationService.deleteByTypeAndRental(NotificationType.RENTAL_APPROVED, dto.getRentalId());


    return ResponseEntity.ok("Time request sent");
  }

  @PostMapping("/mark-as-read")
  public ResponseEntity<?> markAllAsRead(@RequestParam Long userId) {
    notificationService.markAllAsRead(userId);
    return ResponseEntity.ok("Notifications marked as read");
  }
}
