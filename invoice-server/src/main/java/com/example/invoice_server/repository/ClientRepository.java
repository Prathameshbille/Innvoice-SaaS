package com.example.invoice_server.repository;

import com.example.invoice_server.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}
