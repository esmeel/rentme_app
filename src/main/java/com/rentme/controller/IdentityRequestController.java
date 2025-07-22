package com.rentme.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentme.data_transfer_objects.IdentityRequestDTO;
import com.rentme.model.IdentityRequest;
import com.rentme.model.User;
import com.rentme.repository.IdentityRequestRepository;
import com.rentme.repository.UserRepository;
import com.rentme.security.JwtUtil;

@RestController
@RequestMapping("/api")
public class IdentityRequestController {
  @Autowired
  private JwtUtil jwtUtil;


  private final IdentityRequestRepository identityRequestRepository;
  private final UserRepository userRepository;

  public IdentityRequestController(IdentityRequestRepository identityRequestRepository,
      UserRepository userRepository) {
    this.identityRequestRepository = identityRequestRepository;
    this.userRepository = userRepository;
  }

  @PostMapping("/identity-requests")
  public ResponseEntity<?> submitIdentityRequest(@RequestBody IdentityRequestDTO dto,
      Principal principal) {

    User user = userRepository.findByEmail(principal.getName());
    if (user == null)
      return ResponseEntity.status(404).body("User not found");

    IdentityRequest request = new IdentityRequest();
    request.setUser(user);
    request.setName(dto.getName());
    request.setDocumentType(dto.getDocumentType());
    request.setImageUrls(dto.getImageUrls());
    request.setStatus("PENDING");
    request.setSubmittedAt(LocalDateTime.now());

    identityRequestRepository.save(request);

    return ResponseEntity.ok("Identity request submitted successfully");
  }



  @GetMapping("/identity-requests/status")
  public ResponseEntity<?> getUserIdentityStatus(
      @RequestHeader("Authorization") String authHeader) {
    String token = authHeader.replace("Bearer ", "");
    String email = jwtUtil.extractEmail(token);

    User user = userRepository.findByEmail(email);
    if (user == null)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    Optional<IdentityRequest> optionalRequest =
        identityRequestRepository.findTopByUserOrderBySubmittedAtDesc(user);
    if (optionalRequest.isEmpty()) {
      return ResponseEntity.ok(Map.of("status", "NONE"));
    }

    IdentityRequest request = optionalRequest.get();

    String adminNote = "";
    if (request.getAdminNote() != null)
      adminNote = request.getAdminNote();

    return ResponseEntity.ok(Map.of("status", request.getStatus(), "note", request.getAdminNote(),
        "verified", request.isVerified()));
  }


}
