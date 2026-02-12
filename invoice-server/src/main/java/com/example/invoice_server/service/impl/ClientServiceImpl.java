package com.example.invoice_server.service.impl;

import com.example.invoice_server.dto.ClientRequestDTO;
import com.example.invoice_server.entity.Client;
import com.example.invoice_server.entity.Company;
import com.example.invoice_server.repository.ClientRepository;
import com.example.invoice_server.repository.CompanyRepository;
import com.example.invoice_server.service.ClientService;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final CompanyRepository companyRepository;

    public ClientServiceImpl(ClientRepository clientRepository, CompanyRepository companyRepository) {
        this.clientRepository = clientRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public void createClient(ClientRequestDTO request) {

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Client client = new Client();
        client.setName(request.getName());
        client.setEmail(request.getEmail());
        client.setPhone(request.getPhone());
        client.setStreet(request.getStreet());
        client.setCity(request.getCity());
        client.setState(request.getState());
        client.setPincode(request.getPincode());
        client.setCompany(company);

        clientRepository.save(client);

    }
}
