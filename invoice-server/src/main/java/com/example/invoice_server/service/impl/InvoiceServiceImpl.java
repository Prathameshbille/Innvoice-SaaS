package com.example.invoice_server.service.impl;

import com.example.invoice_server.dto.InvoiceItemRequestDTO;
import com.example.invoice_server.dto.InvoiceRequestDTO;
import com.example.invoice_server.entity.*;
import com.example.invoice_server.repository.ClientRepository;
import com.example.invoice_server.repository.CompanyRepository;
import com.example.invoice_server.repository.InvoiceRepository;

import com.example.invoice_server.service.InvoiceService;
import jakarta.transaction.Transactional;
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
}
