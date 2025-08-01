package com.rentme.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rentme.model.User;
import com.rentme.model.UserReview;
import com.rentme.repository.UserReviewRepository;

@Service
public class UserReviewService {

  private final UserReviewRepository userReviewRepository;

  public UserReviewService(UserReviewRepository userReviewRepository) {
    this.userReviewRepository = userReviewRepository;
  }

  public List<UserReview> getAllReviews() {
    return userReviewRepository.findAll();
  }

  public List<UserReview> getReviewsByTargetUser(User user) {
    return userReviewRepository.findByTargetUserId(user.getId());
  }

  public UserReview createReview(UserReview review) {
    return userReviewRepository.save(review);
  }
}
