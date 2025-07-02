package com.rentme.service;

import org.springframework.stereotype.Component;

import com.rentme.model.User;
import com.rentme.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Component
public class AdminProvider {
  private final UserRepository userRepository;
  private User adminUser;

  public AdminProvider(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostConstruct
  public void init() {
    this.adminUser = userRepository.findByRole("ADMIN");
    if (adminUser == null) {
      System.err.println("❌ Admin user not found!");
    }
  }

  public User getAdminUser() {
    return this.adminUser;
  }
}
