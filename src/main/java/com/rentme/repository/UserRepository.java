
package com.rentme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentme.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByRole(String role);

    User findByEmail(String email);

    User getUserById(Long Id);

    void deleteById(Long id);

}
