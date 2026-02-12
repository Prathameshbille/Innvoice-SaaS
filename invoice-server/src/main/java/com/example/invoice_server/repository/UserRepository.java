package com.example.invoice_server.repository;

import com.example.invoice_server.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(
            @Email(message = "Invalid email format")
            @NotBlank(message = "Email is required")

            String email);
}
