package com.example.invoice_server.service;

import com.example.invoice_server.dto.CompanyRequestDTO;

public interface CompanyService {

    void createCompany(CompanyRequestDTO request);
}
