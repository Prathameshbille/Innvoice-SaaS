package com.example.invoice_server.controller;

import com.example.invoice_server.dto.CompanyRequestDTO;
import com.example.invoice_server.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<String> createCompany(
            @Valid @RequestBody CompanyRequestDTO request) {

        companyService.createCompany(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Company created successfully");
    }
}
