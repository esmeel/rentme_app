package com.rentme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentme.model.User;
import com.rentme.model.UserReview;

public interface UserReviewRepository extends JpaRepository<UserReview, Long> {
  List<UserReview> findByTargetUser(User user);

  List<UserReview> findByReviewer(User reviewer);
}
