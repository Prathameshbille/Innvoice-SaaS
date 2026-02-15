package com.example.invoice_server.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateInvoiceStatusDTO {

    @NotNull
    private String status;

}
