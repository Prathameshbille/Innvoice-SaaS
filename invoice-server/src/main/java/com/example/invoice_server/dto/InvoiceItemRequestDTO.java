package com.example.invoice_server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InvoiceItemRequestDTO {

    @NotBlank
    private String itemName;

    @NotNull
    private Integer quantity;

    @NotNull
    private BigDecimal unitPrice;
}
