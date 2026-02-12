package com.example.invoice_server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    private String street;
    private String city;
    private String state;
    private String country;
    private String pincode;

    private String logoUrl;

    @NotNull
    private Long userId;
}
