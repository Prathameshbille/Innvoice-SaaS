package com.example.invoice_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "invoices")
public class Invoice extends BaseEntity {

    @Column(nullable = false)
    private String invoiceNumber;

    @Column(nullable = false)
    private LocalDate issueDate;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;   // DRAFT, SENT, PAID

    private String notes;

    // ---- Calculation Fields ----
    @Column(nullable = false)
    private Double subtotal;

    @Column(nullable = false)
    private Double taxRate;   // overall GST %

    @Column(nullable = false)
    private Double taxAmount;

    @Column(nullable = false)
    private Double totalAmount;

    // ---- Bank Details ----
    private String bankAccountName;
    private String bankAccountNumber;
    private String ifscCode;

    private String templateName;

    // ---- Relationships ----
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceItem> items;
}
