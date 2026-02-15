package com.example.invoice_server.service.impl;

import com.example.invoice_server.dto.InvoiceItemRequestDTO;
import com.example.invoice_server.dto.InvoiceItemResponseDTO;
import com.example.invoice_server.dto.InvoiceRequestDTO;
import com.example.invoice_server.dto.InvoiceResponseDTO;
import com.example.invoice_server.entity.*;
import com.example.invoice_server.mapper.InvoiceMapper;
import com.example.invoice_server.repository.ClientRepository;
import com.example.invoice_server.repository.CompanyRepository;
import com.example.invoice_server.repository.InvoiceRepository;

import com.example.invoice_server.service.InvoiceService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ClientRepository clientRepository;
    private final CompanyRepository companyRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, ClientRepository clientRepository, CompanyRepository companyRepository) {
        this.invoiceRepository = invoiceRepository;

        this.clientRepository = clientRepository;
        this.companyRepository = companyRepository;
    }


    @Override
    @Transactional
    public void createInvoice(InvoiceRequestDTO request){

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (!client.getCompany().getId().equals(company.getId())) {
            throw new RuntimeException("Client does not belong to the selected company");
        }


        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(request.getInvoiceNumber());
        invoice.setIssueDate(request.getIssueDate());
        invoice.setDueDate(request.getDueDate());
        invoice.setNotes(request.getNotes());
        invoice.setTaxRate(request.getTaxRate());
        invoice.setStatus(InvoiceStatus.DRAFT);
        invoice.setBankAccountName(request.getBankAccountName());
        invoice.setBankAccountNumber(request.getBankAccountNumber());
        invoice.setIfscCode(request.getIfscCode());


        invoice.setCompany(company);
        invoice.setClient(client);

        List<InvoiceItem> items = new ArrayList<>();

        BigDecimal subtotal = BigDecimal.ZERO;

        for (InvoiceItemRequestDTO itemDTO : request.getItems()) {

            InvoiceItem item = new InvoiceItem();
            item.setItemName(itemDTO.getItemName());
            item.setQuantity(itemDTO.getQuantity());
            item.setUnitPrice(itemDTO.getUnitPrice());

            BigDecimal lineTotal = itemDTO.getUnitPrice()
                    .multiply(BigDecimal.valueOf(itemDTO.getQuantity()));

            item.setLineTotal(lineTotal);
            item.setInvoice(invoice);

            subtotal = subtotal.add(lineTotal);

            items.add(item);
        }

        invoice.setItems(items);

        BigDecimal taxAmount = subtotal
                .multiply(request.getTaxRate())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);


        BigDecimal totalAmount = subtotal.add(taxAmount);

        invoice.setSubtotal(subtotal);
        invoice.setTaxAmount(taxAmount);
        invoice.setTotalAmount(totalAmount);

        invoiceRepository.save(invoice);

    }

//    @Override
//    public InvoiceResponseDTO getInvoiceById(Long id) {
//
//        Invoice invoice=invoiceRepository.findById(id)
//                .orElseThrow(()->new RuntimeException("Invoice Not Found"));
//
//
//        // Converting InvoiceItem → InvoiceItemResponseDTO
//
//        List<InvoiceItemResponseDTO> itemResponses = invoice.getItems()
//                .stream()
//                .map(item -> {
//
//                    InvoiceItemResponseDTO dto = new InvoiceItemResponseDTO();
//
//                    dto.setItemName(item.getItemName());
//                    dto.setQuantity(item.getQuantity());
//                    dto.setUnitPrice(item.getUnitPrice());
//                    dto.setLineTotal(item.getLineTotal());
//
//                    return dto;
//
//                })
//                .toList();
//
//
//        // Convert Invoice → InvoiceResponseDTO
//        InvoiceResponseDTO response = new InvoiceResponseDTO();
//
//        response.setId(invoice.getId());
//        response.setInvoiceNumber(invoice.getInvoiceNumber());
//        response.setIssueDate(invoice.getIssueDate());
//        response.setDueDate(invoice.getDueDate());
//        response.setStatus(invoice.getStatus().name());
//
//        response.setSubtotal(invoice.getSubtotal());
//        response.setTaxRate(invoice.getTaxRate());
//        response.setTaxAmount(invoice.getTaxAmount());
//        response.setTotalAmount(invoice.getTotalAmount());
//
//        response.setCompanyName(invoice.getCompany().getName());
//        response.setClientName(invoice.getClient().getName());
//
//        response.setItems(itemResponses);
//
//        return response;
//    }  // as before i was not having the mapper.

    @Override
    public InvoiceResponseDTO getInvoiceById(Long id) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice Not Found"));

        return InvoiceMapper.toResponseDTO(invoice);

    }


    @Override

    public List<InvoiceResponseDTO> getAllInvoices() {

        return invoiceRepository.findAll()
                .stream()
                .map(InvoiceMapper::toResponseDTO)
                .toList();

    }

    @Override
    @Transactional
    public void updateInvoice(Long id, InvoiceRequestDTO request) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (!client.getCompany().getId().equals(company.getId())) {
            throw new RuntimeException("Client does not belong to company");
        }


        // Update basic fields

        invoice.setInvoiceNumber(request.getInvoiceNumber());
        invoice.setIssueDate(request.getIssueDate());
        invoice.setDueDate(request.getDueDate());
        invoice.setNotes(request.getNotes());
        invoice.setTaxRate(request.getTaxRate());

        invoice.setCompany(company);
        invoice.setClient(client);



        // Remove old items (IMPORTANT)

        invoice.getItems().clear();



        // Add new items

        BigDecimal subtotal = BigDecimal.ZERO;

        for (InvoiceItemRequestDTO itemDTO : request.getItems()) {

            InvoiceItem item = new InvoiceItem();

            item.setItemName(itemDTO.getItemName());
            item.setQuantity(itemDTO.getQuantity());
            item.setUnitPrice(itemDTO.getUnitPrice());

            BigDecimal lineTotal = itemDTO.getUnitPrice()
                    .multiply(BigDecimal.valueOf(itemDTO.getQuantity()));

            item.setLineTotal(lineTotal);

            item.setInvoice(invoice);

            subtotal = subtotal.add(lineTotal);


            // THIS IS IMPORTANT LINE

            invoice.getItems().add(item);

        }



        // Recalculate totals

        BigDecimal taxAmount = subtotal
                .multiply(request.getTaxRate())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        BigDecimal totalAmount = subtotal.add(taxAmount);



        invoice.setSubtotal(subtotal);
        invoice.setTaxAmount(taxAmount);
        invoice.setTotalAmount(totalAmount);



        invoiceRepository.save(invoice);

    }

    @Override
    public void updateInvoiceStatus(Long id, String status) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        invoice.setStatus(InvoiceStatus.valueOf(status.toUpperCase()));

        invoiceRepository.save(invoice);

    }

    @Override
    public void deleteInvoice(Long id) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        invoiceRepository.delete(invoice);

    }


    @Override
    public List<InvoiceResponseDTO> filterInvoices(String status, Long clientId) {

        List<Invoice> invoices;

        if (status != null && clientId != null) {

            invoices = invoiceRepository.findByStatusAndClientId(
                    InvoiceStatus.valueOf(status),
                    clientId
            );

        }
        else if (status != null) {

            invoices = invoiceRepository.findByStatus(
                    InvoiceStatus.valueOf(status)
            );

        }
        else if (clientId != null) {

            invoices = invoiceRepository.findByClientId(clientId);

        }
        else {

            invoices = invoiceRepository.findAll();

        }

        return invoices
                .stream()
                .map(InvoiceMapper::toResponseDTO)
                .toList();
    }



}
