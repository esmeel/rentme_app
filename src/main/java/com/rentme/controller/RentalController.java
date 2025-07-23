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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentme.data_transfer_objects.ConfirmReceivedDTO;
import com.rentme.data_transfer_objects.MeetingConfirmationDTO;
import com.rentme.data_transfer_objects.RentalRequest;
import com.rentme.data_transfer_objects.RentalResponseDTO;
import com.rentme.data_transfer_objects.RentalResponseRequest;
import com.rentme.data_transfer_objects.ReturnToolRequestDTO;
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
import com.rentme.service.UserService;

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
    public final UserService userService;

    public RentalController(RentalService rentalService, JwtUtil jwtUtil,
            UserRepository userRepository, RentalRepository rentalRepository,
            NotificationRepository notificationRepository, ChatRoomRepository chatRoomRepository,
            ScheduleEntryRepository scheduleEntryRepository,
            NotificationService notificationService, UserService userService) {
        this.rentalService = rentalService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
        this.notificationRepository = notificationRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.scheduleEntryRepository = scheduleEntryRepository;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @PostMapping("/request-mark-returned")
    public ResponseEntity<String> requestMarkReturned(@RequestBody ReturnToolRequestDTO request) {

        Rental rental = rentalRepository.findRentalById(request.getRentalId());
        User sender = userRepository.getUserById(request.getRenterId());
        User receiver = userRepository.getUserById(rental.getOwnerId());
        Notification notif = new Notification();
        notif.setAddress("");
        notif.setCreatedAt(LocalDateTime.now());
        notif.setReceiverId(receiver.getId());
        notif.setSenderId(sender.getId());
        notif.setReceiverName(receiver.getName());
        notif.setSenderName(sender.getName());
        notif.setToolName(rental.getToolName());
        notif.setToolPicUrl(rental.getToolPic());
        notif.setIsRead(false);
        notif.setStarts(rental.getStartDate());
        notif.setEnds(rental.getEndDate());
        notif.setRelatedId(rental.getId());
        notif.setType(NotificationType.RETURN_CONFIRMATION_REQUEST);
        notif.setMessage(sender.getName()
                + " is requesting that you confirm the return of the tool " + rental.getToolName());


        notificationService.sendNotification(notif);

        boolean success =
                rentalService.requestMarkReturned(request.getRentalId(), request.getRenterId());
        if (success) {
            return ResponseEntity.ok("Return request submitted");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not allowed to mark this rental as returned");
        }

    }

    @PostMapping("/request-return")
    public ResponseEntity<String> requestReturn(@RequestBody ReturnToolRequestDTO request,
            @RequestHeader("Authorization") String authHeader) {
        System.out.println("Return request function");

        System.out.println("Return request from" + request.getRentalId());
        boolean success = rentalService.requestReturn(request.getRentalId(), request.getRenterId());
        if (success) {
            return ResponseEntity.ok("Return request registered");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not allowed to request return");
        }
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> rentalExists(@RequestParam Long toolId,
            @RequestParam Long renterId) {
        boolean exists = rentalRepository.existsByToolIdAndRenterId(toolId, renterId);
        return ResponseEntity.ok(exists);
    }

    @PostMapping
    public ResponseEntity<?> createRental(@RequestBody RentalRequest request) {
        boolean exists = rentalRepository.existsByToolIdAndRenterId(request.getToolId(),
                request.getRenterId());

        if (exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("You already submitted a rental request for this tool.");
        }
        Rental rental = rentalService.createRental(request.getToolId(), request.getRenterId(),
                request.getStartDate(), request.getEndDate(), notificationRepository,
                request.getTotalPrice());
        return ResponseEntity.ok(rental);
    }

    // @PutMapping("/{rentalId}/status")
    // public ResponseEntity<Rental> updateRentalStatus(@PathVariable Long rentalId,
    // @RequestParam RentalStatus status) {
    // Rental rental = rentalService.updateStatus(rentalId, status);
    // return ResponseEntity.ok(rental);
    // }

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

    @GetMapping("/my")
    public ResponseEntity<List<RentalResponseDTO>> getMyRentals(
            @RequestHeader("Authorization") String token) {

        User currentUser = userService.getUserFromToken(token);
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<Rental> rentals =
                rentalRepository.findByOwnerIdOrRenterId(currentUser.getId(), currentUser.getId());
        if (rentals.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }


        List<RentalResponseDTO> response =
                rentals.stream().map(RentalResponseDTO::new).collect(Collectors.toList());
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        System.out.println("Rentals body returned:" + response.toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{rentalId}/confirm-return")
    public ResponseEntity<?> confirmToolReturned(@PathVariable Long rentalId,
            HttpServletRequest request) {
        String token = jwtUtil.extractToken(request);
        System.out.println("confirm-return, Token:" + token);

        String email = jwtUtil.extractEmail(token);
        return rentalService.confirmReturn(rentalId, email);
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
        List<RentalResponseDTO> dtos =
                rentals.stream().map(RentalResponseDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/respond")
    @Transactional
    public ResponseEntity<?> respondToRentalRequest(@RequestBody RentalResponseRequest request) {

        Optional<Rental> rentalOpt = rentalRepository.findById(request.getRentalId());
        if (rentalOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rental request not found");
        }
        Rental rental = rentalOpt.get();
        rental.setStatus(request.isAccept() ? RentalStatus.ACCEPTED : RentalStatus.REJECTED);
        rental.setAccepted(request.isAccept());

        rentalRepository.save(rental);

        if (request.isAccept()) {
            if (rental.getTool() != null) {
                rental.getTool().setAvailable(false);
            }
        } else {

            rental.getTool().setAvailable(true);
            scheduleEntryRepository.deleteByRentalId(rental.getId());
            rentalRepository.deleteById(rental.getId());
        }


        User renter = userRepository.getUserById(rental.getRenterId());
        User owner = userRepository.getUserById(rental.getOwnerId());

        Notification notification = new Notification();
        notification.setSenderId(request.getSenderId());
        notification.setReceiverId(request.getReceiverId());
        notification.setToolPicUrl(rental.getToolPic());
        notification.setSenderName(owner.getName());
        notification.setReceiverName(renter.getName());
        notification.setType(request.isAccept() ? NotificationType.RENTAL_APPROVED
                : NotificationType.RENTAL_REJECTED);
        notification.setRelatedId(rental.getId());
        notification.setMessage(request.isAccept()
                ? owner.getName() + " accepted to rent you " + rental.getTool().getName()
                : owner.getName() + " rejected / canceled to rent you "
                        + rental.getTool().getName());
        notification.setCreatedAt(LocalDateTime.now());
        notification.setIsRead(false);
        notification.setStarts(request.getStarts());;
        notification.setEnds(request.getEnds());;
        notification.setToolName(rental.getTool().getName());
        notification.setToolPicUrl(rental.getTool().getImageUrl());
        notification.setToolId(rental.getTool().getId());
        notification.setTotalPrice(rental.getTotalPrice());


        notificationRepository.save(notification);
        notificationRepository.deleteByTypeAndRelatedId(NotificationType.RENTAL_REQUEST,
                rental.getId());

        return ResponseEntity.ok("Response recorded and notification sent.");

    }

    @PostMapping("/confirm-meeting")
    @Transactional

    public ResponseEntity<?> confirmMeeting(@RequestBody MeetingConfirmationDTO dto) {
        System.out.println("rentalId========================= " + dto.getRentalId());

        ScheduleEntry entry = scheduleEntryRepository.findFirstByRentalId(dto.getRentalId());
        if (entry == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No schedule entry found for this rental.");
        }


        dto.setMeetingDate(entry.getDate());
        dto.setMeetingHourFrom(entry.getFromTime());
        dto.setMeetingHourTo(entry.getToTime());
        entry.setConfirmed(true);
        scheduleEntryRepository.delete(entry);

        notificationService.sendNotification(dto);
        return ResponseEntity.ok("Meeting confirmed and renter notified.");
    }

    @PostMapping("/confirm-received")
    public ResponseEntity<String> confirmToolReceived(@RequestBody ConfirmReceivedDTO request) {
        Optional<Rental> rentalOpt = rentalRepository.findById(request.getRentalId());
        if (rentalOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Rental not found.");
        }

        Rental rental = rentalOpt.get();

        // تحقق أن المستخدم الذي يؤكّد هو نفسه المستأجر
        if (!rental.getRenterId().equals(request.getUserId())) {
            return ResponseEntity.status(403)
                    .body("You are not authorized to confirm this rental.");
        }

        rental.setStatus(RentalStatus.ACTIVE);
        rentalRepository.save(rental);

        // إرسال إشعار للمالك

        notificationService.sendNotification(request);



        return ResponseEntity.ok("Rental confirmed and notification sent.");
    }

}

