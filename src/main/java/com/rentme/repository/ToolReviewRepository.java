package com.rentme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentme.model.Tool;
import com.rentme.model.ToolReview;
import com.rentme.model.User;

public interface ToolReviewRepository extends JpaRepository<ToolReview, Long> {
  List<ToolReview> findByTool(Tool tool);

  List<ToolReview> findByReviewer(User reviewer);
}
