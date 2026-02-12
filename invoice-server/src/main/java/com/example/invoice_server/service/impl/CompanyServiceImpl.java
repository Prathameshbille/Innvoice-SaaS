package com.example.invoice_server.service.impl;

import com.example.invoice_server.dto.CompanyRequestDTO;
import com.example.invoice_server.entity.Company;
import com.example.invoice_server.entity.User;
import com.example.invoice_server.repository.CompanyRepository;
import com.example.invoice_server.repository.UserRepository;
import com.example.invoice_server.service.CompanyService;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createCompany(CompanyRequestDTO request) {

        User user=userRepository
                .findById(request.getUserId())
                .orElseThrow(()->new RuntimeException("User Not Found"));


        Company company = new Company();
        company.setName(request.getName());
        company.setEmail(request.getEmail());
        company.setStreet(request.getStreet());
        company.setCity(request.getCity());
        company.setState(request.getState());
        company.setCountry(request.getCountry());
        company.setPincode(request.getPincode());
        company.setLogoUrl(request.getLogoUrl());
        company.setUser(user);

        companyRepository.save(company);
    }
}
