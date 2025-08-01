package com.rentme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentme.model.UserReview;

public interface UserReviewRepository extends JpaRepository<UserReview, Long> {

  List<UserReview> findByTargetUserId(Long targetUserId);

  List<UserReview> findByReviewerId(Long reviewerId);
}
