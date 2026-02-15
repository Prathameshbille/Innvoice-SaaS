package com.example.invoice_server.controller;

import com.example.invoice_server.dto.InvoiceRequestDTO;
import com.example.invoice_server.dto.InvoiceResponseDTO;
import com.example.invoice_server.dto.UpdateInvoiceStatusDTO;
import com.example.invoice_server.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<String> createInvoice(@Valid @RequestBody InvoiceRequestDTO request){
        invoiceService.createInvoice(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Invoice Created Successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> getInvoice(@PathVariable Long id){
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @GetMapping
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoices(){
        return ResponseEntity.ok(
                invoiceService.getAllInvoices()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateInvoice(@PathVariable Long id,
                                                @Valid @RequestBody InvoiceRequestDTO request){

        invoiceService.updateInvoice(id, request);

        return ResponseEntity.ok("Invoice Updated Successfully");

    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateInvoiceStatusDTO request) {

        invoiceService.updateInvoiceStatus(id, request.getStatus());

        return ResponseEntity.ok("Invoice status updated");
    }

}
