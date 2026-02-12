package com.example.invoice_server.controller;

import com.example.invoice_server.dto.ClientRequestDTO;
import com.example.invoice_server.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService=clientService;
    }

    @PostMapping
    public ResponseEntity<String> createClient(
            @Valid @RequestBody ClientRequestDTO request) {

        clientService.createClient(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Client created successfully");
    }

}
