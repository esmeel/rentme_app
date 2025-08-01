package com.rentme.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentme.data_transfer_objects.UserReviewRequestDTO;
import com.rentme.model.User;
import com.rentme.model.UserReview;
import com.rentme.repository.UserRepository;
import com.rentme.repository.UserReviewRepository;
import com.rentme.service.UserReviewService;
import com.rentme.service.UserService;

@RestController
@RequestMapping("/api/user-reviews")
public class UserReviewController {

  private final UserReviewService userReviewService;
  private final UserReviewRepository userReviewRepository;
  private final UserRepository userRepository;
  private final UserService userService;

  public UserReviewController(UserReviewService userReviewService,
      UserReviewRepository userReviewRepository, UserRepository userRepository,
      UserService userService) {
    this.userReviewService = userReviewService;
    this.userReviewRepository = userReviewRepository;
    this.userRepository = userRepository;
    this.userService = userService;
  }

  @PostMapping("/{userId}")
  public ResponseEntity<?> createUserReview(@PathVariable Long userId,
      @RequestBody UserReviewRequestDTO dto) {
    User targetUser = userRepository.findById(dto.getTargetUserId()).orElse(null);
    User reviewer = userRepository.findById(dto.getReviewerId()).orElse(null);
    System.out.println("--------------------");
    System.out.println("Data:" + dto.toString());
    System.out.println();
    /*
     * 'targetUserId': user!.id, 'reviewerId': AppGlobals.currentUser.id, 'rating':
     * userRating.toInt(),
     */
    if (targetUser == null || reviewer == null) {
      System.out.println("");
      System.out.println("");
      System.out.println("Not valid date: Target id:" + targetUser.toString() + "reviewer id: "
          + reviewer.toString());
      System.out.println("");
      System.out.println("");



      return ResponseEntity.badRequest().body("User not found");
    }

    UserReview review = new UserReview();
    review.setTargetUserId(targetUser.getId());
    review.setReviewerId(reviewer.getId());
    review.setRating(dto.getRating());

    userReviewRepository.save(review);

    // ✅ التحديث عبر userService
    userService.updateUserRating(targetUser, dto.getRating());
    return ResponseEntity.ok("User review submitted successfully");
  }

  @GetMapping("/target/{targetUserId}")
  public ResponseEntity<?> getReviewsByTargetUser(@PathVariable Long targetUserId) {
    User targetUser = userRepository.findById(targetUserId).orElse(null);
    if (targetUser == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(userReviewRepository.findByTargetUserId(targetUserId));
  }
}
