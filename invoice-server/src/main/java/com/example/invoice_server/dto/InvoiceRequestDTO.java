package com.example.invoice_server.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class InvoiceRequestDTO {

    @NotNull
    private String invoiceNumber;

    @NotNull
    private LocalDate issueDate;

    private LocalDate dueDate;

    private String notes;

    @NotNull
    private BigDecimal taxRate;

    private String bankAccountName;
    private String bankAccountNumber;
    private String ifscCode;



    @NotNull
    private Long companyId;

    @NotNull
    private Long clientId;

    @NotNull
    private List<InvoiceItemRequestDTO> items;
}
