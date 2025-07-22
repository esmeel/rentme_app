package com.rentme.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rentme.data_transfer_objects.ScheduleConfirmationRequest;
import com.rentme.data_transfer_objects.ScheduleEntryDTO;
import com.rentme.data_transfer_objects.ScheduleRequestDTO;
import com.rentme.model.Notification;
import com.rentme.model.NotificationType;
import com.rentme.model.ScheduleEntry;
import com.rentme.model.User;
import com.rentme.repository.ScheduleEntryRepository;
import com.rentme.repository.UserRepository;

@Service
public class ScheduleService {

  private final UserRepository userRepository;
  private final ScheduleEntryRepository scheduleEntryRepository;
  private final NotificationService notificationService;

  public ScheduleService(UserRepository userRepository,
      ScheduleEntryRepository scheduleEntryRepository, NotificationService notificationService) {
    this.userRepository = userRepository;
    this.scheduleEntryRepository = scheduleEntryRepository;
    this.notificationService = notificationService;
  }

  public void processSchedule(ScheduleRequestDTO request) {
    User sender = userRepository.findById(request.getSenderId()).orElse(null);
    User receiver = userRepository.findById(request.getReceiverId()).orElse(null);

    if (sender == null || receiver == null) {
      throw new RuntimeException("Invalid sender or receiver");
    }

    List<ScheduleEntry> entriesToSave = new ArrayList<>();

    for (ScheduleEntryDTO dto : request.getEntries()) {
      ScheduleEntry entry = new ScheduleEntry();
      entry.setDate(dto.getDate());
      entry.setFromTime(dto.getFrom());
      entry.setToTime(dto.getTo());
      entry.setSenderId(receiver.getId());
      entry.setReceiverId(sender.getId());
      entry.setRentalId(request.getRentalId());
      entry.setRelatedId(request.getRelatedId());
      entry.setNotes(request.getNotes());
      entry.setConfirmed(false);
      entriesToSave.add(entry);
    }

    scheduleEntryRepository.saveAll(entriesToSave);
    notificationService.sendNotification(request);
  }

  public List<ScheduleEntry> getByRentalId(Long rentalId) {
    return scheduleEntryRepository.findByRentalId(rentalId);
  }

  public List<ScheduleEntry> getSchedulesForReceiver(Long receiverId) {
    User receiver = userRepository.findById(receiverId).orElse(null);
    if (receiver == null)
      return new ArrayList<>();
    return scheduleEntryRepository.findByReceiverId(receiver.getId());
  }

  /**
   * تابع تأكيد خيار الموعد الذي اختاره المستأجر. يتم تعيين الخيار المختار كـ confirmed، وقد نرغب
   * أيضًا بإلغاء التأكيد عن الخيارات الأخرى.
   */
  public void addSelectedSchedule(Long scheduleId, Long rentalId, Long userId,
      ScheduleConfirmationRequest request) {
    ScheduleEntry selected = scheduleEntryRepository.findById(scheduleId)
        .orElseThrow(() -> new RuntimeException("Schedule entry not found"));

    if (!Objects.equals(selected.getRentalId(), rentalId)) {
      throw new RuntimeException("Rental ID mismatch");
    }

    // تعيين الخيار المختار كـ confirmed.
    // selected.setConfirmed(true);
    scheduleEntryRepository.save(selected);

    // إلغاء التأكيد عن باقي الخيارات في نفس الإيجار (إذا رغبت في ذلك)
    List<ScheduleEntry> allEntries = scheduleEntryRepository.findByRentalId(rentalId);
    List<ScheduleEntry> otherEntries =
        allEntries.stream().filter(e -> !e.getId().equals(scheduleId)).collect(Collectors.toList());
    for (ScheduleEntry entry : otherEntries) {
      if (entry.isConfirmed()) {
        entry.setConfirmed(false);
      }
    }
    scheduleEntryRepository.saveAll(otherEntries);
    notificationService.sendNotification(request);
    // notificationService.sendNotification(selected.getSenderId(), userId,
    // NotificationType.OWNER_TIME_RESPONSE, "The renter selected a schedule time.", rentalId);

    notificationService.deleteByTypeAndRental(NotificationType.OWNER_TIME_RESPONSE, rentalId);
  }
}
