package com.example.invoice_server.repository;

import com.example.invoice_server.entity.Invoice;
import com.example.invoice_server.entity.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {

    List<Invoice> findByStatus(InvoiceStatus status);

    List<Invoice> findByClientId(Long clientId);

    List<Invoice> findByStatusAndClientId(InvoiceStatus status, Long clientId);
}
