package com.rentme.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rentme.model.Tool;
import com.rentme.model.ToolReview;
import com.rentme.repository.ToolReviewRepository;

@Service
public class ToolReviewService {

  private final ToolReviewRepository toolReviewRepository;

  public ToolReviewService(ToolReviewRepository toolReviewRepository) {
    this.toolReviewRepository = toolReviewRepository;
  }

  public List<ToolReview> getAllReviews() {
    return toolReviewRepository.findAll();
  }

  public List<ToolReview> getReviewsByToolId(Tool tool) {
    return toolReviewRepository.findByTool(tool);
  }

  public List<ToolReview> getReviewsByTool(Tool tool) {
    return toolReviewRepository.findByTool(tool);
  }


  public ToolReview createReview(ToolReview review) {
    return toolReviewRepository.save(review);
  }
}
