package com.pdev.ecdemo.controllers;

import com.pdev.ecdemo.entities.Client;
import com.pdev.ecdemo.entities.Invoice;
import com.pdev.ecdemo.services.ClientService;
import com.pdev.ecdemo.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController @RequestMapping(path = "/api/v1/invoices")
public class InvoiceController extends _GenericController<Invoice> {

    private final InvoiceService invoiceService;
    private final ClientService clientService;

    @Autowired
    public InvoiceController(InvoiceService service, ClientService clientService) {
        super(service);
        this.invoiceService = service;
        this.clientService = clientService;
    }


   /* @GetMapping(path = "/all")
    public ResponseEntity<List<Invoice>> findAll() {
        try {
            List<Invoice> invoices = invoiceService.findAll();
            return ResponseEntity.ok(invoices);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }*/

    @PostMapping(path = "/create/client/{clientId}")
    public ResponseEntity<Invoice> create(@PathVariable Long clientId) {
        try {
            Invoice invoice = invoiceService.createInvoice(clientId);
            return ResponseEntity.status(HttpStatus.CREATED).body(invoice);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /*@GetMapping(path = "find/{id}")
    public ResponseEntity<Invoice> findById(@PathVariable Long id) {
        try {
            Optional<Invoice> invoice = invoiceService.findById(id);
            if (invoice.isPresent()) {
                return ResponseEntity.ok(invoice.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }*/

    @GetMapping(path = "/client/{clientId}")
    public ResponseEntity<List<Invoice>> findByClientId(@PathVariable Long clientId) {
        try {

            Optional<Client> client = clientService.getEntityById(clientId);
            if (!client.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            List<Invoice> invoices = invoiceService.findByClient(client.get());

            return ResponseEntity.ok(invoices);

        } catch (Exception e) {

            return ResponseEntity.badRequest().build();
        }
    }

    /*@DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Invoice> delete(@PathVariable Long id) {
        try {
            Optional<Invoice> invoice = invoiceService.deleteInvoice(id);
            if (invoice.isPresent()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }*/
}