package com.example.invoice_server.service;

import com.example.invoice_server.dto.ClientRequestDTO;

public interface ClientService {

    void createClient(ClientRequestDTO request);
}
