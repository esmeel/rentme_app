package com.rentme.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentme.data_transfer_objects.MeetingConfirmationDTO;
import com.rentme.data_transfer_objects.RentalRequest;
import com.rentme.data_transfer_objects.RentalResponseDTO;
import com.rentme.data_transfer_objects.RentalResponseRequest;
import com.rentme.model.ChatRoom;
import com.rentme.model.Notification;
import com.rentme.model.NotificationType;
import com.rentme.model.Rental;
import com.rentme.model.RentalStatus;
import com.rentme.model.ScheduleEntry;
import com.rentme.model.User;
import com.rentme.repository.ChatRoomRepository;
import com.rentme.repository.NotificationRepository;
import com.rentme.repository.RentalRepository;
import com.rentme.repository.ScheduleEntryRepository;
import com.rentme.repository.UserRepository;
import com.rentme.security.JwtUtil;
import com.rentme.service.NotificationService;
import com.rentme.service.RentalService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RentalService rentalService;
    private final RentalRepository rentalRepository;
    private final NotificationRepository notificationRepository;
    public final ChatRoomRepository chatRoomRepository;
    public final ScheduleEntryRepository scheduleEntryRepository;
    public final NotificationService notificationService;

    public RentalController(
            RentalService rentalService,
            JwtUtil jwtUtil,
            UserRepository userRepository,
            RentalRepository rentalRepository,
            NotificationRepository notificationRepository,
            ChatRoomRepository chatRoomRepository,
            ScheduleEntryRepository scheduleEntryRepository,
            NotificationService notificationService) {
        this.rentalService = rentalService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
        this.notificationRepository = notificationRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.scheduleEntryRepository = scheduleEntryRepository;
        this.notificationService = notificationService;
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> rentalExists(
            @RequestParam Long toolId,
            @RequestParam Long renterId) {
        boolean exists = rentalRepository.existsByToolIdAndRenterId(
                toolId, renterId);
        return ResponseEntity.ok(exists);
    }

    @PostMapping
    public ResponseEntity<?> createRental(@RequestBody RentalRequest request) {
        boolean exists = rentalRepository.existsByToolIdAndRenterId(
                request.getToolId(), request.getRenterId());

        if (exists) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("You already submitted a rental request for this tool.");
        }
        Rental rental = rentalService.createRental(
                request.getToolId(),
                request.getRenterId(),
                request.getStartDate(),
                request.getEndDate(),
                notificationRepository);
        System.out.println(rental.toString());
        return ResponseEntity.ok(rental);
    }

    @PutMapping("/{rentalId}/status")
    public ResponseEntity<Rental> updateRentalStatus(
            @PathVariable Long rentalId,
            @RequestParam RentalStatus status) {
        Rental rental = rentalService.updateStatus(rentalId, status);
        return ResponseEntity.ok(rental);
    }

    @GetMapping("/my-tools-requests")
    public ResponseEntity<?> getRequestsForMyTools(HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromRequest(request);
        String email = jwtUtil.extractEmail(token);
        User owner = userRepository.findByEmail(email);
        if (owner == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Owner not found");
        }

        List<Rental> requests = rentalRepository.findByOwnerId(owner.getId());
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/incoming")
    public ResponseEntity<?> getIncomingRequests(HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromRequest(request);
        String email = jwtUtil.extractEmail(token);
        User owner = userRepository.findByEmail(email);
        System.out.println("Owner of rental " + owner);
        if (owner == null) {
            return ResponseEntity.status(404).body("Owner not found");
        }
        List<Rental> rentals = rentalService.getIncomingRequests(owner);
        List<RentalResponseDTO> dtos = rentals.stream()
                .map(RentalResponseDTO::new)
                .collect(Collectors.toList());
        System.out.println("To be sent:==> " + dtos);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/respond")
    @Transactional
    public ResponseEntity<?> respondToRentalRequest(
            @RequestBody RentalResponseRequest request) {

        Optional<Rental> rentalOpt = rentalRepository.findById(request.getRentalId());
        if (rentalOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Rental request not found");
        }
        Rental rental = rentalOpt.get();
        rental.setStatus(request.isAccept() ? RentalStatus.ACCEPTED : RentalStatus.REJECTED);
        rental.setAccepted(request.isAccept());

        rentalRepository.save(rental);

        if (request.isAccept()) {
            if (rental.getTool() != null) {
                rental.getTool().setAvailable(false);
            }

            boolean chatRoomExists = chatRoomRepository.findByRentalId(rental.getId()).isPresent();
            if (!chatRoomExists) {
                ChatRoom room = new ChatRoom();
                room.setOwner(rental.getOwner());
                room.setRenter(rental.getRenter());
                room.setRental(rental);
                room.setCreatedAt(LocalDateTime.now());
                chatRoomRepository.save(room);
            }
        } else {
            rental.getTool().setAvailable(true);
            chatRoomRepository.findByRentalId(rental.getId())
                    .ifPresent(chatRoomRepository::delete);

            List<ScheduleEntry> entries = scheduleEntryRepository.findByRentalId(rental.getId());
            scheduleEntryRepository.deleteAll(entries);

            rentalRepository.deleteById(rental.getId());
        }

        Long chatRoomId = null;
        if (request.isAccept()) {
            Optional<ChatRoom> chatRoomOpt = chatRoomRepository.findByRentalId(rental.getId());
            if (chatRoomOpt.isPresent()) {
                chatRoomId = chatRoomOpt.get().getId();
            }
        }

        Notification notification = new Notification();

        notification.setSenderId(request.getSenderId());
        notification.setReceiverId(request.getReceiverId());
        notification.setToolPicUrl(rental.getToolPic());
        notification.setSenderName(rental.getOwner().getName());
        notification.setReceiverName(rental.getRenter().getName());
        notification.setType(request.isAccept() ? NotificationType.RENTAL_APPROVED : NotificationType.RENTAL_REJECTED);
        notification.setRelatedId(rental.getId());
        notification.setMessage(request.isAccept()
                ? rental.getOwner().getName() + " accepted to rent you " + rental.getTool().getName()
                : rental.getOwner().getName() + " rejected to rent you " + rental.getTool().getName());
        notification.setCreatedAt(LocalDateTime.now());
        notification.setIsRead(false);
        notificationRepository.save(notification);
        notificationRepository.deleteByTypeAndRelatedId(NotificationType.RENTAL_REQUEST, rental.getId());

        return ResponseEntity.ok("Response recorded and notification sent.");
    }

    @PostMapping("/confirm-meeting")
    public ResponseEntity<?> confirmMeeting(@RequestBody MeetingConfirmationDTO dto) {
        System.out.println("rentalId========================= " + dto.getRentalId());

        ScheduleEntry entry = scheduleEntryRepository.findByRentalId(dto.getRentalId()).get(0);
        entry.setConfirmed(true);
        scheduleEntryRepository.save(entry);

        scheduleEntryRepository.deleteByRentalIdAndIdNot(entry.getRentalId(), entry.getId());
        User sender = userRepository.findById(entry.getSenderId())
                .orElseThrow(() -> new RuntimeException("Receiver user not found"));

        notificationService.sendNotification(
                entry.getReceiverId(),
                dto.getUserId(),
                NotificationType.FINAL_SCHEDULE_CONFIRMED,
                "You will meet with " + sender.getName() + " at: " + entry.getTimeInfo()
                        + "\nPlease confirm receiving ONLY when you recewivr the tool, to begin the rental.",
                entry.getRentalId());
        notificationService.deleteByTypeAndRental(NotificationType.SCHEDULE_PROPOSAL, dto.getRentalId());
        return ResponseEntity.ok("Meeting confirmed and renter notified.");
    }
}
