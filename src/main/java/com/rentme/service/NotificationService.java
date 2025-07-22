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
import com.rentme.model.User;
import com.rentme.repository.NotificationRepository;
import com.rentme.repository.RentalRepository;
import com.rentme.repository.UserRepository;

@Service
public class NotificationService {

  @Autowired
  private NotificationRepository notificationRepo;
  private final NotificationRepository notificationRepository;
  private final UserRepository userRepository;
  private final RentalRepository rentalRepository;

  public NotificationService(NotificationRepository notificationRepository,
      UserRepository userRepository, RentalRepository rentalRepository) {
    this.notificationRepository = notificationRepository;
    this.userRepository = userRepository;
    this.rentalRepository = rentalRepository;
  }



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

    if (dto instanceof ReturnToolRequestDTO returnToolRequestDTO)
      hundelReturnToolRequestNotif(returnToolRequestDTO);

    if (dto instanceof ScheduleConfirmationRequest scheduleConfirmationRequest)
      hundelScheduleConfirmationRequestNotif(scheduleConfirmationRequest);

    if (dto instanceof ScheduleEntryDTO scheduleEntryDTO)
      hundelScheduleEntryNotif(scheduleEntryDTO);

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
    throw new UnsupportedOperationException("Unimplemented method 'hundelIdentityRequestDTONotif'");
  }

  private void hundelUserResponseNotif(UserResponseDTO userResponseDTO) {

    // No need to implement this method as per the current context

    throw new UnsupportedOperationException("Unimplemented method 'hundelUserResponseNotif'");
  }

  private void hundelToolResponseNotif(ToolResponseDTO toolResponseDTO) {


    throw new UnsupportedOperationException("Unimplemented method 'hundelToolResponseNotif'");
  }

  private void hundelScheduleRequestNotif(ScheduleRequestDTO scheduleRequestDTO) {
    Notification prevNotification =
        notificationRepository.findByTypeAndRelatedId(NotificationType.OWNER_TIME_REQUEST,
            scheduleRequestDTO.getRentalId()).stream().findFirst().orElse(null);
    if (prevNotification == null) {
      throw new RuntimeException("Previous notification not found");
    }
    Notification notification = new Notification();
    notification.setType(NotificationType.SCHEDULE_PROPOSAL);
    notification.setSenderId(scheduleRequestDTO.getReceiverId());
    notification.setReceiverId(scheduleRequestDTO.getSenderId());
    notification
        .setMessage("You have a new schedule proposal from " + prevNotification.getSenderName());
    notification.setRelatedId(scheduleRequestDTO.getRentalId());
    notification.setCreatedAt(java.time.LocalDateTime.now());
    notification.setIsRead(false);
    notification.setToolId(prevNotification != null ? prevNotification.getToolId() : -1);
    notification.setToolPicUrl(prevNotification != null ? prevNotification.getToolPicUrl() : "");
    notification.setToolName(prevNotification != null ? prevNotification.getToolName() : "");
    notification.setAddress(prevNotification != null ? prevNotification.getAddress() : "");
    notification.setNotes(prevNotification != null ? prevNotification.getNotes() : "");
    notification.setSenderName(prevNotification.getSenderName());
    notification.setReceiverName(prevNotification.getReceiverName());
    notification.setStarts(prevNotification.getStarts());
    notification.setEnds(prevNotification.getEnds());
    notification.setTotalPrice(prevNotification.getTotalPrice());
    notificationRepository.save(notification);

    // Delete the previous notification
    if (prevNotification != null)
      notificationRepository.deleteByTypeAndRelatedId(prevNotification.getType(),
          scheduleRequestDTO.getRentalId());

  }

  private void hundelScheduleEntryNotif(ScheduleEntryDTO scheduleEntryDTO) {

    throw new UnsupportedOperationException("Unimplemented method 'hundelScheduleEntryNotif'");
  }

  private void hundelScheduleConfirmationRequestNotif(
      ScheduleConfirmationRequest rcheduleConfirmationRequest) {

    throw new UnsupportedOperationException(
        "Unimplemented method 'hundelScheduleConfirmationRequestNotif'");
  }

  private void hundelReturnToolRequestNotif(ReturnToolRequestDTO returnToolRequestDTO) {

    throw new UnsupportedOperationException("Unimplemented method 'hundelReturnToolRequestNotif'");
  }

  private void hundelReportRequestDTONotif(ReportRequestDTO reportRequestDTO) {

    throw new UnsupportedOperationException("Unimplemented method 'hundelReportRequestDTONotif'");
  }

  private void hundelRentalResponseRequestNotif(RentalResponseRequest rentalResponseRequest) {

    throw new UnsupportedOperationException(
        "Unimplemented method 'hundelRentalResponseRequestNotif'");
  }

  private void hundelRentalResponseNotif(RentalResponseDTO rentalResponseDTO) {

    throw new UnsupportedOperationException("Unimplemented method 'hundelRentalResponseNotif'");
  }

  private void hundelrentalRequestNotif(RentalRequest rentalRequest) {

    throw new UnsupportedOperationException("Unimplemented method 'hundelrentalRequestNotif'");
  }

  private void hundelMeetingConfirmationNotif(MeetingConfirmationDTO meetingConfirmationDTO) {

    throw new UnsupportedOperationException(
        "Unimplemented method 'hundelMeetingConfirmationNotif'");
  }

  private void hundelLocationSendRequestNotif(LocationSendRequestDTO dto) {

    throw new UnsupportedOperationException(
        "Unimplemented method 'hundelLocationSendRequestNotif'");
  }

  private void hundelConfirmReceivedNotif(ConfirmReceivedDTO dto) {

    throw new UnsupportedOperationException("Not supported yet.");
  }

  private void hundelLocationOrTimeRequesNotif(LocationOrTimeRequestDTO dto) {

    throw new UnsupportedOperationException("Not supported yet.");
  }

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
