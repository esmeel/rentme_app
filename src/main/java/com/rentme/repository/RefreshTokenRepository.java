package com.rentme.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentme.model.RefreshToken;
import com.rentme.model.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByToken(String token);

  Optional<RefreshToken> findByUser(User user);
}
