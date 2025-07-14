
package com.rentme.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rentme.model.Notification;
import com.rentme.model.NotificationType;
import com.rentme.model.Rental;
import com.rentme.model.RentalStatus;
import static com.rentme.model.RentalStatus.ACCEPTED;
import static com.rentme.model.RentalStatus.CANCELLED;
import static com.rentme.model.RentalStatus.COMPLETED;
import static com.rentme.model.RentalStatus.IN_PROGRESS;
import static com.rentme.model.RentalStatus.REJECTED;
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
            java.time.LocalDate endDate, NotificationRepository notificationRepository) {

        this.tool = toolRepository.findById(toolId)
                .orElseThrow(() -> new RuntimeException("Tool not found"));

        User renter = userRepository.findById(renterId)
                .orElseThrow(() -> new RuntimeException("Renter not found"));

        User owner = tool.getOwner();

        Rental rental = new Rental();
        rental.setTool(tool);
        rental.setToolPic(tool.getImageUrl());
        rental.setRenterId(renter.getId());
        rental.setOwnerId(owner.getId());
        rental.setStartDate(startDate);
        rental.setEndDate(endDate);
        rental.setStatus(RentalStatus.PENDING);
        rental.setCreatedAt(LocalDateTime.now());

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

        notificationRepository.save(notification);
        /*
         * notificationService.sendNotification( owner.getId(), renter.getId(),
         * NotificationType.RENTAL_REQUEST, "New rental request for your tool: " + tool.getName());
         */
        return rental;
    }

    // تحديث حالة طلب الاستئجار
    public Rental updateStatus(Long rentalId, RentalStatus status) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        rental.setStatus(status);
        rentalRepository.save(rental);

        // إرسال إشعار للمستأجر حسب الحالة
        String message;
        switch (status) {
            case ACCEPTED -> message = "Your rental request has been accepted.";
            case REJECTED -> message = "Your rental request has been rejected.";
            case IN_PROGRESS -> message = "Your rental has started.";
            case COMPLETED -> message = "Your rental has been completed.";
            case CANCELLED -> message = "Your rental has been cancelled.";
            default -> message = "";
        };

        if (!message.isEmpty()) {
            notificationService.sendNotification(rental.getRenterId(), rental.getOwnerId(),
                    NotificationType.RENTAL_APPROVED, message, rentalId);
        }

        return rental;
    }
}
