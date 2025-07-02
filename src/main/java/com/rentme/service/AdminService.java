package com.rentme.service;

import org.springframework.stereotype.Service;

import com.rentme.model.User;
import com.rentme.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AdminService {

  private final UserRepository userRepository;
  private User adminUser;

  public AdminService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostConstruct
  public void init() {
    this.adminUser = userRepository.findByRole("ADMIN");
  }

  public User getAdminUser() {
    return this.adminUser;
  }
}
