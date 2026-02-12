package com.example.invoice_server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    private String phone;

    private String street;
    private String city;
    private String state;
    private String pincode;

    @NotNull
    private Long companyId;
}
