package com.example.invoice_server.mapper;

import com.example.invoice_server.dto.InvoiceItemResponseDTO;
import com.example.invoice_server.dto.InvoiceResponseDTO;
import com.example.invoice_server.entity.Invoice;
import com.example.invoice_server.entity.InvoiceItem;

import java.util.List;

public class InvoiceMapper {

    public static InvoiceResponseDTO toResponseDTO(Invoice invoice) {

        List<InvoiceItemResponseDTO> itemResponses =
                invoice.getItems()
                        .stream()
                        .map(InvoiceMapper::toItemResponseDTO)
                        .toList();

        InvoiceResponseDTO response = new InvoiceResponseDTO();

        response.setId(invoice.getId());
        response.setInvoiceNumber(invoice.getInvoiceNumber());
        response.setIssueDate(invoice.getIssueDate());
        response.setDueDate(invoice.getDueDate());
        response.setStatus(invoice.getStatus().name());

        response.setSubtotal(invoice.getSubtotal());
        response.setTaxRate(invoice.getTaxRate());
        response.setTaxAmount(invoice.getTaxAmount());
        response.setTotalAmount(invoice.getTotalAmount());

        response.setCompanyName(invoice.getCompany().getName());
        response.setClientName(invoice.getClient().getName());

        response.setItems(itemResponses);

        return response;
    }


    public static InvoiceItemResponseDTO toItemResponseDTO(InvoiceItem item) {

        InvoiceItemResponseDTO dto = new InvoiceItemResponseDTO();

        dto.setItemName(item.getItemName());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setLineTotal(item.getLineTotal());

        return dto;
    }

}
