package com.example.invoice_server.service;

import com.example.invoice_server.dto.InvoiceRequestDTO;
import org.springframework.stereotype.Service;


public interface InvoiceService {

    void createInvoice(InvoiceRequestDTO request);
}
