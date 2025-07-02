package com.rentme.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rentme.model.IdentityRequest;
import com.rentme.model.NotificationType;
import com.rentme.model.User;
import com.rentme.repository.IdentityRequestRepository;
import com.rentme.repository.UserRepository;
import com.rentme.security.JwtUtil;
import com.rentme.service.AdminProvider;
import com.rentme.service.NotificationService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminProvider adminProvider;
    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    private IdentityRequestRepository identityRequestRepository;

    public AdminController(NotificationService notificationService,
            UserRepository userRepository,
            JwtUtil jwtUtil) {
        this.notificationService = notificationService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/identity-requests")
    public String showPendingRequests(Model model) {
        List<IdentityRequest> requests = identityRequestRepository.findByStatus("PENDING");
        model.addAttribute("requests", requests);
        return "identity_requests";
    }

    @PostMapping("/identity-requests/{id}/approve")
    public String approveRequest(
            @PathVariable Long id,
            @RequestParam(required = false) String note) {

        IdentityRequest request = identityRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus("APPROVED");
        request.setReviewedAt(LocalDateTime.now());
        request.setAdminNote(note);
        identityRequestRepository.save(request);

        User user = request.getUser();
        if (user != null) {
            user.setVerified(true);
            user.setIdVerifyTime(LocalDateTime.now());
            userRepository.save(user);
        }

        notificationService.sendNotification(
                user.getId(),
                adminProvider.getAdminUser().getId(), // نظام
                NotificationType.SYSTEM,
                "✅ Your identity verification request has been approved." +
                        (note != null ? "\nNote: " + note : ""),
                0L);

        System.out.println("✅ Approval notification sent");
        return "redirect:/admin/identity-requests";
    }

    @PostMapping("/identity-requests/{id}/reject")
    public String rejectRequest(
            @PathVariable Long id,
            @RequestParam(required = false) String note) {

        IdentityRequest request = identityRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus("REJECTED");
        request.setReviewedAt(LocalDateTime.now());
        request.setAdminNote(note);
        identityRequestRepository.save(request);

        User user = request.getUser();

        notificationService.sendNotification(
                user.getId(),
                adminProvider.getAdminUser().getId(),
                NotificationType.SYSTEM,
                "❌ Your identity verification request has been rejected." +
                        (note != null ? "\nNote: " + note : ""),
                0L);

        System.out.println("❌ Rejection notification sent");
        return "redirect:/admin/identity-requests";
    }

}
