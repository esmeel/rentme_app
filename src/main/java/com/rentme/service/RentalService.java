
package com.rentme.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rentme.model.Notification;
import com.rentme.model.NotificationType;
import com.rentme.model.Rental;
import com.rentme.model.RentalStatus;
import com.rentme.model.Tool;
import com.rentme.model.User;
import com.rentme.repository.NotificationRepository;
import com.rentme.repository.RentalRepository;
import com.rentme.repository.ToolRepository;
import com.rentme.repository.UserRepository;

import jakarta.persistence.JoinColumn;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final ToolRepository toolRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    // @ManyToOne
    @JoinColumn(name = "tool_id")
    private Tool tool;

    public RentalService(RentalRepository rentalRepository, ToolRepository toolRepository,
            UserRepository userRepository, NotificationService notificationService) {
        this.rentalRepository = rentalRepository;
        this.toolRepository = toolRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public List<Rental> getIncomingRequests(User owner) {
        System.out.println("🔍 Looking for rentals with owner ID: " + owner.getId());
        List<Rental> results =
                rentalRepository.findRentalByOwnerIdAndStatus(owner.getId(), RentalStatus.PENDING);
        System.out.println("🔍 Found " + results.size() + " pending requests.");
        return results;

    }

    // إنشاء طلب استئجار جديد
    public Rental createRental(Long toolId, Long renterId, java.time.LocalDate startDate,
            java.time.LocalDate endDate, NotificationRepository notificationRepository,
            double totalPrice) {

        this.tool = toolRepository.findById(toolId)
                .orElseThrow(() -> new RuntimeException("Tool not found"));

        User renter = userRepository.findById(renterId)
                .orElseThrow(() -> new RuntimeException("Renter not found"));

        User owner = tool.getOwner();

        Rental rental = new Rental();
        rental.setTool(tool);
        rental.setToolPic(tool.getImageUrl());
        rental.setToolName(tool.getName());

        rental.setRenterId(renter.getId());
        rental.setOwnerId(owner.getId());
        rental.setRenterName(renter.getName());
        rental.setOwnerName(owner.getName());

        rental.setStartDate(startDate);
        rental.setEndDate(endDate);
        rental.setStatus(RentalStatus.PENDING);
        rental.setCreatedAt(LocalDateTime.now());
        rental.setTotalPrice(totalPrice);

        rentalRepository.save(rental);
        Notification notification = new Notification();
        notification.setSenderId(renter.getId()); // أو من JWT
        notification.setSenderName(renter.getName()); // أو من JWT
        notification.setReceiverId(tool.getOwner().getId());
        notification.setReceiverName(tool.getOwner().getName());
        notification.setToolPicUrl(tool.getImageUrl());
        notification.setToolName(tool.getName());
        notification.setType(NotificationType.RENTAL_REQUEST);
        notification.setMessage(renter.getName() + " want to rent your " + tool.getName() + " from "
                + startDate + " to " + endDate);
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setStarts(startDate);
        notification.setEnds(endDate);

        notification.setRelatedId(rental.getId());
        notification.setTotalPrice(totalPrice);
        notification.setToolId(toolId);

        notificationRepository.save(notification);
        /*
         * notificationService.sendNotification( owner.getId(), renter.getId(),
         * NotificationType.RENTAL_REQUEST, "New rental request for your tool: " + tool.getName());
         */
        return rental;
    }

    public boolean requestMarkReturned(Long rentalId, Long userId) {
        Optional<Rental> optionalRental = rentalRepository.findById(rentalId);
        if (optionalRental.isEmpty())
            return false;

        Rental rental = optionalRental.get();

        // تأكّد أن userId هو المستأجر وليس المالك
        if (!rental.getRenterId().equals(userId)) {
            return false;
        }

        // تحدّث الحالة أو تُسجّل طلب الإرجاع بأي شكل يناسب منطقك
        rental.setRequestedReturnByRenter(true); // يفترض أن هذا الحقل موجود
        rentalRepository.save(rental);
        return true;
    }


    public ResponseEntity<?> confirmReturn(Long rentalId, String email) {
        Optional<Rental> rentalOpt = rentalRepository.findById(rentalId);
        if (rentalOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rental not found");
        }

        Rental rental = rentalOpt.get();
        // get the owner email by id
        User owner = userRepository.getUserById(rental.getOwnerId());
        // التحقّق من أنّ المستخدم هو المالك
        if (!email.equals(owner.getEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Only the owner can confirm return");
        }

        if (!rental.isRequestedReturnByRenter()) {
            return ResponseEntity.badRequest()
                    .body("Tool has not been requested for return by renter");
        }

        rental.setConfirmedReturnedByOwner(true);
        rental.setStatus(RentalStatus.ENDED);
        rental.setToolAvailable(true); // إعادة الأداة كمتاحة
        rentalRepository.save(rental);
        notificationService.deleteByTypeAndRental(NotificationType.RETURN_CONFIRMATION_REQUEST,
                rentalId);
        return ResponseEntity.ok("Tool return confirmed successfully");
    }


    public boolean requestReturn(Long rentalId, Long userId) {
        Optional<Rental> rentalOpt = rentalRepository.findById(rentalId);

        if (rentalOpt.isEmpty())
            return false;

        Rental rental = rentalOpt.get();

        // فقط المالك هو من يطلب إرجاع الأداة
        if (!rental.getRenterId().equals(userId))
            return false;

        rental.setRequestingReturn(true);
        rentalRepository.save(rental);
        return true;
    }

    // تحديث حالة طلب الاستئجار
    // public Rental updateStatus(Long rentalId, RentalStatus status) {
    // Rental rental = rentalRepository.findById(rentalId)
    // .orElseThrow(() -> new RuntimeException("Rental not found"));

    // rental.setStatus(status);
    // rentalRepository.save(rental);

    // // إرسال إشعار للمستأجر حسب الحالة
    // String message;
    // switch (status) {
    // case ACCEPTED -> message = "Your rental request has been accepted.";
    // case REJECTED -> message = "Your rental request has been rejected.";
    // case IN_PROGRESS -> message = "Your rental has started.";
    // case COMPLETED -> message = "Your rental has been completed.";
    // case CANCELLED -> message = "Your rental has been cancelled.";
    // default -> message = "";
    // };

    // if (!message.isEmpty()) {
    // notificationService.sendNotification(rental.getRenterId(), rental.getOwnerId(),
    // NotificationType.RENTAL_APPROVED, message, rentalId);
    // }

    // return rental;
    // }
}
