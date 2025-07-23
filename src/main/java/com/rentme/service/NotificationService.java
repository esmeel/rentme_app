package com.rentme.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentme.data_transfer_objects.ConfirmReceivedDTO;
import com.rentme.data_transfer_objects.IdentityRequestDTO;
import com.rentme.data_transfer_objects.LocationOrTimeRequestDTO;
import com.rentme.data_transfer_objects.LocationSendRequestDTO;
import com.rentme.data_transfer_objects.MeetingConfirmationDTO;
import com.rentme.data_transfer_objects.RentalRequest;
import com.rentme.data_transfer_objects.RentalResponseDTO;
import com.rentme.data_transfer_objects.RentalResponseRequest;
import com.rentme.data_transfer_objects.ReportRequestDTO;
import com.rentme.data_transfer_objects.ReturnToolRequestDTO;
import com.rentme.data_transfer_objects.ScheduleConfirmationRequest;
import com.rentme.data_transfer_objects.ScheduleEntryDTO;
import com.rentme.data_transfer_objects.ScheduleRequestDTO;
import com.rentme.data_transfer_objects.ToolResponseDTO;
import com.rentme.data_transfer_objects.UserResponseDTO;
import com.rentme.model.Notification;
import com.rentme.model.NotificationType;
import com.rentme.model.ScheduleEntry;
import com.rentme.model.User;
import com.rentme.repository.NotificationRepository;
import com.rentme.repository.RentalRepository;
import com.rentme.repository.ScheduleEntryRepository;
import com.rentme.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class NotificationService {

  @Autowired
  private NotificationRepository notificationRepo;
  private final NotificationRepository notificationRepository;
  private final UserRepository userRepository;
  private final RentalRepository rentalRepository;
  private final ScheduleEntryRepository scheduleEntryRepository;

  public NotificationService(NotificationRepository notificationRepository,
      UserRepository userRepository, RentalRepository rentalRepository,
      ScheduleEntryRepository scheduleEntryRepository) {
    this.notificationRepository = notificationRepository;
    this.userRepository = userRepository;
    this.rentalRepository = rentalRepository;
    this.scheduleEntryRepository = scheduleEntryRepository;
  }


  // @Transactional
  public void sendNotification(Object dto) {

    if (dto instanceof ConfirmReceivedDTO confirmReceivedDTO)
      hundelConfirmReceivedNotif(confirmReceivedDTO);

    if (dto instanceof IdentityRequestDTO identityRequestDTO)
      hundelIdentityRequestDTONotif(identityRequestDTO);

    if (dto instanceof LocationOrTimeRequestDTO locationOrTimeRequestDTO)
      hundelLocationOrTimeRequesNotif(locationOrTimeRequestDTO);

    if (dto instanceof LocationSendRequestDTO locationSendRequestDTO)
      hundelLocationSendRequestNotif(locationSendRequestDTO);

    if (dto instanceof MeetingConfirmationDTO meetingConfirmationDTO)
      hundelMeetingConfirmationNotif(meetingConfirmationDTO);

    if (dto instanceof RentalRequest rentalRequest)
      hundelrentalRequestNotif(rentalRequest);

    if (dto instanceof RentalResponseDTO rentalResponseDTO)
      hundelRentalResponseNotif(rentalResponseDTO);

    if (dto instanceof RentalResponseRequest rentalResponseRequest)
      hundelRentalResponseRequestNotif(rentalResponseRequest);

    if (dto instanceof ReportRequestDTO reportRequestDTO)
      hundelReportRequestDTONotif(reportRequestDTO);

    // if (dto instanceof ReturnToolRequestDTO returnToolRequestDTO)
    // hundelReturnToolRequestNotif(returnToolRequestDTO);

    if (dto instanceof ScheduleConfirmationRequest scheduleConfirmationRequest)
      hundelScheduleConfirmationRequestNotif(scheduleConfirmationRequest);

    // if (dto instanceof ScheduleEntryDTO scheduleEntryDTO)
    // hundelScheduleEntryNotif(scheduleEntryDTO);

    if (dto instanceof ScheduleRequestDTO scheduleRequestDTO)
      hundelScheduleRequestNotif(scheduleRequestDTO);

    if (dto instanceof ToolResponseDTO toolResponseDTO)
      hundelToolResponseNotif(toolResponseDTO);

    if (dto instanceof UserResponseDTO userResponseDTO)
      hundelUserResponseNotif(userResponseDTO);

    if (dto instanceof UserResponseDTO userResponseDTO)
      hundelUserResponseNotif(userResponseDTO);



  }

  private void hundelIdentityRequestDTONotif(IdentityRequestDTO identityRequestDTO) {

    // No need to implement this method as per the current context
  }

  private void hundelUserResponseNotif(UserResponseDTO userResponseDTO) {

    // No need to implement this method as per the current context

  }

  private void hundelToolResponseNotif(ToolResponseDTO toolResponseDTO) {


  }

  private void hundelScheduleRequestNotif(ScheduleRequestDTO scheduleRequestDTO) {
    Notification prevNotification =
        notificationRepository.findByTypeAndRelatedId(NotificationType.OWNER_TIME_REQUEST,
            scheduleRequestDTO.getRentalId()).get(0);
    if (prevNotification == null) {
      throw new RuntimeException("Previous notification not found");
    }
    Notification notification = new Notification();
    notification.setType(NotificationType.SCHEDULE_PROPOSAL);
    notification.setSenderId(prevNotification.getReceiverId());
    notification.setReceiverId(prevNotification.getSenderId());
    notification
        .setMessage("You have a new schedule proposal from " + prevNotification.getSenderName());
    notification.setRelatedId(prevNotification.getRelatedId());
    notification.setCreatedAt(java.time.LocalDateTime.now());
    notification.setIsRead(false);
    notification.setToolId(prevNotification != null ? prevNotification.getToolId() : -1);
    notification.setToolPicUrl(prevNotification != null ? prevNotification.getToolPicUrl() : "");
    notification.setToolName(prevNotification != null ? prevNotification.getToolName() : "");
    notification.setAddress(prevNotification != null ? prevNotification.getAddress() : "");
    notification.setNotes(prevNotification != null ? prevNotification.getNotes() : "");
    notification.setSenderName(prevNotification.getReceiverName());
    notification.setReceiverName(prevNotification.getSenderName());
    notification.setStarts(prevNotification.getStarts());
    notification.setEnds(prevNotification.getEnds());
    notification.setTotalPrice(prevNotification.getTotalPrice());
    notificationRepository.save(notification);

    // Delete the previous notification
    if (prevNotification != null)
      notificationRepository.delete(prevNotification);

  }

  /*
   * private void hundelScheduleEntryNotif(ScheduleEntryDTO dto) { Notification prev =
   * notificationRepository .findByTypeAndRelatedId(NotificationType.SCHEDULE_PROPOSAL,
   * prev.getRelatedId()).get(0); if (prev == null) throw new
   * RuntimeException("Previous notification not found");
   *
   * Notification n = new Notification(); n.setType(NotificationType.SCHEDULE_CONFIRMED);
   * n.setSenderId(prev.getReceiverId()); n.setReceiverId(prev.getSenderId());
   * n.setSenderName(prev.getSenderName()); n.setReceiverName(prev.getReceiverName());
   * n.setMessage("Schedule confirmed by " + prev.getSenderName()); n.setToolId(prev.getToolId());
   * n.setToolName(prev.getToolName()); n.setToolPicUrl(prev.getToolPicUrl());
   * n.setAddress(prev.getAddress()); n.setNotes(prev.getNotes()); n.setStarts(prev.getStarts());
   * n.setEnds(prev.getEnds()); n.setTotalPrice(prev.getTotalPrice());
   * n.setRelatedId(prev.getRelatedId()); n.setCreatedAt(java.time.LocalDateTime.now());
   * n.setIsRead(false); n.setTimeRequested(true); notificationRepository.save(n);
   * notificationRepository.deleteByTypeAndRelatedId(NotificationType.SCHEDULE_PROPOSAL,
   * prev.getRelatedId()); }
   *
   */


  private void hundelScheduleConfirmationRequestNotif(ScheduleConfirmationRequest dto) {
    Notification prev = notificationRepository
        .findByTypeAndRelatedId(NotificationType.SCHEDULE_PROPOSAL, dto.getRentalId()).stream()
        .findFirst().orElse(null);
    if (prev == null)
      throw new RuntimeException("Previous notification not found");

    ScheduleEntry selected = scheduleEntryRepository.findEnteryById(dto.getScheduleId());

    String message = prev.getSenderName() + " wants to meet with you at " + selected.getDate()
        + ", between " + selected.getFromTime() + " and " + selected.getToTime() + ".\n";// ;+
                                                                                         // dto.get
    Notification n = new Notification();
    n.setType(NotificationType.OWNER_TIME_RESPONSE);
    n.setSenderId(prev.getReceiverId());
    n.setReceiverId(prev.getSenderId());
    n.setSenderName(prev.getReceiverName());
    n.setReceiverName(prev.getSenderName());
    n.setMessage(message);
    n.setToolId(prev.getToolId());
    n.setToolName(prev.getToolName());
    n.setToolPicUrl(prev.getToolPicUrl());
    n.setAddress(prev.getAddress());
    n.setNotes(prev.getNotes());
    n.setStarts(prev.getStarts());
    n.setEnds(prev.getEnds());
    n.setTotalPrice(prev.getTotalPrice());
    n.setRelatedId(dto.getRentalId());
    n.setCreatedAt(java.time.LocalDateTime.now());
    n.setIsRead(false);
    System.out.println("Winn send now:");
    System.out.println(notificationRepository.save(n));
    System.out.println("🔔 Sent notification: " + n);
    notificationRepository.deleteByTypeAndRelatedId(prev.getType(), prev.getRelatedId());
  }



  /*
   * private void hundelReturnToolRequestNotif(ReturnToolRequestDTO dto) { Notification prev =
   * notificationRepository .findByTypeAndRelatedId(NotificationType.RENTAL_STARTED,
   * dto.getRentalId()).stream() .findFirst().orElse(null); if (prev == null) throw new
   * RuntimeException("Previous notification not found");
   *
   * Notification n = new Notification(); n.setType(NotificationType.RETURN_REQUEST);
   * n.setSenderId(dto.getSenderId()); n.setReceiverId(dto.getReceiverId());
   * n.setSenderName(prev.getSenderName()); n.setReceiverName(prev.getReceiverName());
   * n.setMessage("Return request sent by " + prev.getSenderName()); n.setToolId(prev.getToolId());
   * n.setToolName(prev.getToolName()); n.setToolPicUrl(prev.getToolPicUrl());
   * n.setAddress(prev.getAddress()); n.setLatitude(prev.getLatitude());
   * n.setLongitude(prev.getLongitude()); n.setNotes(prev.getNotes());
   * n.setStarts(prev.getStarts()); n.setEnds(prev.getEnds());
   * n.setTotalPrice(prev.getTotalPrice()); n.setRelatedId(dto.getRentalId());
   * n.setCreatedAt(java.time.LocalDateTime.now()); n.setIsRead(false);
   * notificationRepository.save(n);
   * notificationRepository.deleteByTypeAndRelatedId(NotificationType.RENTAL_STARTED,
   * prev.getRelatedId()); }
   *
   *
   */
  private void hundelReportRequestDTONotif(ReportRequestDTO reportRequestDTO) {


  }

  private void hundelRentalResponseRequestNotif(RentalResponseRequest rentalResponseRequest) {

  }

  private void hundelRentalResponseNotif(RentalResponseDTO rentalResponseDTO) {

  }

  private void hundelrentalRequestNotif(RentalRequest rentalRequest) {

  }

  private void hundelMeetingConfirmationNotif(MeetingConfirmationDTO dto) {
    Notification prev = notificationRepository
        .findByTypeAndRelatedId(NotificationType.OWNER_TIME_RESPONSE, dto.getRentalId()).stream()
        .findFirst().orElse(null);
    if (prev == null)
      throw new RuntimeException("Previous notification not found");

    Notification n = new Notification();
    n.setType(NotificationType.FINAL_SCHEDULE_CONFIRMED);
    n.setSenderId(prev.getReceiverId());
    n.setReceiverId(prev.getSenderId());
    n.setSenderName(prev.getSenderName());
    n.setReceiverName(prev.getReceiverName());
    n.setMessage(
        "You will meet " + prev.getSenderName() + " at " + dto.getMeetingDate() + ", between "
            + dto.getMeetingHourFrom() + " and " + dto.getMeetingHourTo() + ".\n" + dto.getNotes());
    n.setToolId(prev.getToolId());
    n.setFinalMeetingDate(dto.getMeetingDate());
    n.setFinalMeetingToHour(dto.getMeetingHourTo());
    n.setFinalMeetingFromHour(dto.getMeetingHourFrom());
    n.setToolName(prev.getToolName());
    n.setToolPicUrl(prev.getToolPicUrl());
    n.setAddress(prev.getAddress());
    n.setNotes(dto.getNotes());
    n.setStarts(prev.getStarts());
    n.setEnds(prev.getEnds());
    n.setTotalPrice(prev.getTotalPrice());
    n.setRelatedId(dto.getRentalId());
    n.setCreatedAt(java.time.LocalDateTime.now());
    n.setIsRead(false);
    n.setFinalMeetingDate(dto.getMeetingDate());
    n.setFinalMeetingFromHour(dto.getMeetingHourFrom());
    n.setFinalMeetingToHour(dto.getMeetingHourTo());
    notificationRepository.save(n);
    notificationRepository.deleteByTypeAndRelatedId(prev.getType(), prev.getRelatedId());
  }


  private void hundelLocationSendRequestNotif(LocationSendRequestDTO dto) {
    Notification prev = notificationRepository
        .findByTypeAndRelatedId(NotificationType.OWNER_LOCATION_REQUEST, dto.getRentalId()).stream()
        .findFirst().orElse(null);
    if (prev == null)
      throw new RuntimeException("Previous notification not found");

    Notification n = new Notification();
    n.setType(NotificationType.LOCATION_SENT);
    n.setSenderId(prev.getReceiverId());
    n.setReceiverId(prev.getSenderId());
    n.setSenderName(prev.getSenderName());
    n.setReceiverName(prev.getReceiverName());
    n.setMessage("Location sent by " + prev.getSenderName());
    n.setToolId(prev.getToolId());
    n.setToolName(prev.getToolName());
    n.setToolPicUrl(prev.getToolPicUrl());
    n.setAddress(dto.getAddress());
    n.setLatitude(dto.getLatitude());
    n.setLongitude(dto.getLongitude());
    n.setNotes(prev.getNotes());
    n.setStarts(prev.getStarts());
    n.setEnds(prev.getEnds());
    n.setTotalPrice(prev.getTotalPrice());
    n.setRelatedId(dto.getRentalId());
    n.setCreatedAt(java.time.LocalDateTime.now());
    n.setIsRead(false);
    n.setFinalMeetingDate(prev.getFinalMeetingDate());
    n.setFinalMeetingFromHour(prev.getFinalMeetingFromHour());
    n.setFinalMeetingToHour(prev.getFinalMeetingToHour());



    notificationRepository.save(n);
    notificationRepository.deleteByTypeAndRelatedId(NotificationType.OWNER_LOCATION_REQUEST,
        prev.getRelatedId());

  }

  private void hundelConfirmReceivedNotif(ConfirmReceivedDTO dto) {
    Notification prev = notificationRepository
        .findByTypeAndRelatedId(NotificationType.LOCATION_SENT, dto.getRentalId()).stream()
        .findFirst().orElse(null);
    if (prev == null)
      throw new RuntimeException("Previous notification not found");

    Notification n = new Notification();
    n.setType(NotificationType.TOOL_RECEIVED);
    n.setSenderId(prev.getReceiverId());
    n.setReceiverId(prev.getSenderId());
    n.setSenderName(prev.getSenderName());
    n.setReceiverName(prev.getReceiverName());
    n.setMessage("Tool received confirmed by " + prev.getSenderName());
    n.setToolId(prev.getToolId());
    n.setToolName(prev.getToolName());
    n.setToolPicUrl(prev.getToolPicUrl());
    n.setAddress(prev.getAddress());
    n.setNotes(prev.getNotes());
    n.setStarts(prev.getStarts());
    n.setEnds(prev.getEnds());
    n.setTotalPrice(prev.getTotalPrice());
    n.setRelatedId(dto.getRentalId());
    n.setCreatedAt(java.time.LocalDateTime.now());
    n.setIsRead(false);
    notificationRepository.save(n);
    n.setFinalMeetingDate(prev.getFinalMeetingDate());
    n.setFinalMeetingFromHour(prev.getFinalMeetingFromHour());
    n.setFinalMeetingToHour(prev.getFinalMeetingToHour());
  }

  private void hundelLocationOrTimeRequesNotif(LocationOrTimeRequestDTO dto) {

    NotificationType prevType = NotificationType.RENTAL_APPROVED;
    NotificationType type = dto.getNofitiType().equals("OWNER_LOCATION_REQUEST")
        ? NotificationType.OWNER_LOCATION_REQUEST
        : NotificationType.OWNER_TIME_REQUEST;


    if (type == NotificationType.OWNER_TIME_REQUEST)
      prevType = NotificationType.RENTAL_APPROVED;
    if (type == NotificationType.OWNER_LOCATION_REQUEST)
      prevType = NotificationType.FINAL_SCHEDULE_CONFIRMED;

    Notification prev = notificationRepository.findByTypeAndRelatedId(prevType, dto.getRentalId())
        .stream().findFirst().orElse(null);


    if (prev == null)
      throw new RuntimeException("Previous notification not found");

    Notification n = new Notification();
    n.setType(type);
    n.setSenderId(dto.getSenderId());
    n.setReceiverId(dto.getReceiverId());
    n.setSenderName(prev.getSenderName());
    n.setReceiverName(prev.getReceiverName());
    n.setMessage(type == NotificationType.OWNER_LOCATION_REQUEST
        ? prev.getSenderName() + " is Requesting your location"
        : prev.getSenderName() + " is Requesting your time");
    n.setToolId(prev.getToolId());
    n.setToolName(prev.getToolName());
    n.setToolPicUrl(prev.getToolPicUrl());
    n.setAddress(prev.getAddress());
    n.setNotes(prev.getNotes());
    n.setStarts(prev.getStarts());
    n.setEnds(prev.getEnds());
    n.setTotalPrice(prev.getTotalPrice());
    n.setRelatedId(dto.getRentalId());
    n.setCreatedAt(java.time.LocalDateTime.now());
    n.setIsRead(false);
    n.setFinalMeetingDate(prev.getFinalMeetingDate());
    n.setFinalMeetingFromHour(prev.getFinalMeetingFromHour());
    n.setFinalMeetingToHour(prev.getFinalMeetingToHour());
    notificationRepository.delete(prev);
    notificationRepository.save(n);
  }

  // /*
  // * private void hundelLocationOrTimeRequesNotif(LocationOrTimeRequestDTO dto) { NotificationType
  // * type = dto.isLocation() ? NotificationType.OWNER_LOCATION_REQUEST :
  // * NotificationType.OWNER_TIME_REQUEST;
  // *
  // * Notification prev = notificationRepository
  // * .findByTypeAndRelatedId(NotificationType.RENTAL_APPROVED, dto.getRentalId()).stream()
  // * .findFirst().orElse(null); if (prev == null) throw new
  // * RuntimeException("Previous notification not found");
  // *
  // * Notification n = new Notification(); n.setType(type); n.setSenderId(dto.getSenderId());
  // * n.setReceiverId(dto.getReceiverId()); n.setSenderName(prev.getSenderName());
  // * n.setReceiverName(prev.getReceiverName()); n.setMessage((dto.isLocation() ?
  // * "Requesting location from " : "Requesting time from ") + prev.getReceiverName());
  // * n.setToolId(prev.getToolId()); n.setToolName(prev.getToolName());
  // * n.setToolPicUrl(prev.getToolPicUrl()); n.setAddress(prev.getAddress());
  // * n.setNotes(prev.getNotes()); n.setStarts(prev.getStarts()); n.setEnds(prev.getEnds());
  // * n.setTotalPrice(prev.getTotalPrice()); n.setRelatedId(dto.getRentalId());
  // * n.setCreatedAt(java.time.LocalDateTime.now()); n.setIsRead(false);
  // * notificationRepository.save(n); }
  // */

  public void deleteByTypeAndRental(NotificationType type, Long rentalId) {
    List<Notification> notifs = notificationRepository.findByTypeAndRelatedId(type, rentalId);
    if (notifs.isEmpty())
      return;

    notificationRepository.deleteAll(notifs);
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
