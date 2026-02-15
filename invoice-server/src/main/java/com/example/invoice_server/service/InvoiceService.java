package com.example.invoice_server.service;

import com.example.invoice_server.dto.InvoiceRequestDTO;
import com.example.invoice_server.dto.InvoiceResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface InvoiceService {

    void createInvoice(InvoiceRequestDTO request);
    InvoiceResponseDTO getInvoiceById(Long id);
    List<InvoiceResponseDTO> getAllInvoices();
    void updateInvoice(Long id,InvoiceRequestDTO request);
    void updateInvoiceStatus(Long id, String status);
    void deleteInvoice(Long id);

    List<InvoiceResponseDTO> filterInvoices(String status, Long clientId);

}
