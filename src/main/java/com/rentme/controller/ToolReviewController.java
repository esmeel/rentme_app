package com.rentme.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentme.data_transfer_objects.ToolReviewRequestDTO;
import com.rentme.data_transfer_objects.ToolReviewResponseDTO;
import com.rentme.model.Tool;
import com.rentme.model.ToolReview;
import com.rentme.model.User;
import com.rentme.repository.ToolRepository;
import com.rentme.repository.UserRepository;
import com.rentme.service.ToolReviewService;
import com.rentme.service.ToolService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/tool-reviews")
public class ToolReviewController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ToolRepository toolRepository;

  @Autowired
  private final ToolService toolService;

  private final ToolReviewService toolReviewService;

  public ToolReviewController(ToolReviewService toolReviewService, ToolService toolService) {
    this.toolReviewService = toolReviewService;
    this.toolService = toolService;
  }

  @GetMapping
  public List<ToolReview> getAllReviews() {
    return toolReviewService.getAllReviews();
  }

  // private final ToolService toolService;

  @GetMapping("/{toolId}")
  public List<ToolReviewResponseDTO> getReviewsByTool(@PathVariable Long toolId) {
    Tool tool = toolService.findById(toolId);
    List<ToolReview> reviews = toolReviewService.getReviewsByTool(tool);
    return reviews.stream()
        .map(ToolReviewResponseDTO::new)
        .collect(Collectors.toList());
  }



  @PostMapping("/{toolId}")
  // public ResponseEntity<?> createToolReview(@RequestBody ToolReview review) {
  // System.out.println(review.toString());
  // return ResponseEntity.ok(toolReviewService.createReview(review));
  // }

  // @PostMapping("/tool")
  public ResponseEntity<?> addToolReview(@RequestBody ToolReviewRequestDTO dto,
      HttpServletRequest request) {
    Tool tool = toolRepository.findById(dto.getToolId()).orElse(null);
    User reviewer = userRepository.findById(dto.getReviewerId()).orElse(null);

    if (tool == null || reviewer == null) {
      return ResponseEntity.badRequest().body("Tool or Reviewer not found");
    }

    ToolReview review = new ToolReview();
    review.setTool(tool);
    review.setReviewer(reviewer);
    review.setRating(dto.getRating());
    review.setComment(dto.getComment());

    ToolReview saved = toolReviewService.createReview(review);
    return ResponseEntity.ok(saved);
  }

}
