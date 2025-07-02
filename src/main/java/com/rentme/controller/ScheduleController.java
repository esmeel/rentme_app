package com.rentme.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rentme.data_transfer_objects.ScheduleConfirmationRequest;
import com.rentme.data_transfer_objects.ScheduleRequestDTO;
import com.rentme.model.NotificationType;
import com.rentme.model.Rental;
import com.rentme.model.ScheduleEntry;
import com.rentme.model.User;
import com.rentme.repository.RentalRepository;
import com.rentme.repository.ScheduleEntryRepository;
import com.rentme.repository.UserRepository;
import com.rentme.service.NotificationService;
import com.rentme.service.ScheduleService;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

  private final ScheduleService scheduleService;
  private final ScheduleEntryRepository scheduleEntryRepository;
  private final RentalRepository rentalRepository;
  private final UserRepository userRepository;
  private final NotificationService notificationService;

  public ScheduleController(ScheduleService scheduleService, ScheduleEntryRepository scheduleEntryRepository,
      RentalRepository rentalRepository,
      UserRepository userRepository,
      NotificationService notificationService) {
    this.scheduleService = scheduleService;
    this.scheduleEntryRepository = scheduleEntryRepository;
    this.rentalRepository = rentalRepository;
    this.userRepository = userRepository;
    this.notificationService = notificationService;
  }

  @GetMapping("/receiver/{receiverId}")
  public ResponseEntity<?> getSchedulesForReceiver(@PathVariable Long receiverId) {
    List<ScheduleEntry> entries = scheduleService.getSchedulesForReceiver(receiverId);
    return ResponseEntity.ok(entries);
  }

  @PostMapping("/send")
  public ResponseEntity<String> sendSchedule(@RequestBody ScheduleRequestDTO request) {
    scheduleService.processSchedule(request);
    return ResponseEntity.ok("Schedule received and saved successfully");
  }

  @GetMapping("/rental/{rentalId}")
  public ResponseEntity<List<ScheduleEntry>> getSchedulesByRental(@PathVariable Long rentalId) {
    List<ScheduleEntry> schedules = scheduleService.getByRentalId(rentalId);
    return ResponseEntity.ok(schedules);
  }

  @PostMapping("/selected")
  public ResponseEntity<?> addSelectedSchedule(@RequestBody ScheduleConfirmationRequest request) {
    try {

      scheduleService.addSelectedSchedule(
          request.getScheduleId(),
          request.getRentalId(),
          request.getUserId());
      return ResponseEntity.ok("Schedule confirmed successfully.");
    } catch (Exception e) {
      return ResponseEntity
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error: " + e.getMessage());
    }
  }

  @PostMapping("/confirm")
  public ResponseEntity<?> confirmSchedule(@RequestParam Long scheduleEntryId) {
    ScheduleEntry entry = scheduleEntryRepository.findById(scheduleEntryId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entry not found"));

    entry.setConfirmed(true);
    scheduleEntryRepository.save(entry);

    // Reject all other proposals for same rental
    scheduleEntryRepository.deleteByRentalIdAndIdNot(entry.getRentalId(), entry.getId());

    // Get rental info
    Rental rental = rentalRepository.findById(entry.getRentalId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rental not found"));

    // Get users
    User sender = userRepository.findById(entry.getSenderId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender not found"));
    User receiver = userRepository.findById(entry.getReceiverId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receiver not found"));

    // Compose message
    String dateTime = entry.getDate() + " " + entry.getFromTime(); // Customize formatting as needed
    String message = "You will meet with " + sender.getName() + " at " + dateTime +
        "Important: You must tap 'Confirm Received' once you receive the tool.";

    // Send notification to renter (the receiver of the original proposal)
    notificationService.sendNotification(
        receiver.getId(),
        sender.getId(),
        NotificationType.SCHEDULE_CONFIRMED,
        message,
        rental.getId());

    return ResponseEntity.ok("Schedule confirmed and renter notified.");
  }

}
