package com.pdev.ecdemo.controller;

import com.pdev.ecdemo.dto.InvoiceDTO;
import com.pdev.ecdemo.entity.Invoice;
import com.pdev.ecdemo.service.ClientService;
import com.pdev.ecdemo.service.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/invoices")
public class InvoiceController extends _GenericController<Invoice, InvoiceDTO> {

    private final InvoiceService invoiceService;

    private final ClientService clientService;

    @Autowired
    public InvoiceController(InvoiceService service, ClientService clientService) {
        super(service);
        this.invoiceService = service;
        this.clientService = clientService;
    }

    @Operation(
            summary = "Crear una factura para un cliente / Create an invoice for a client",
            description = "Crea una nueva factura para el cliente especificado por el ID del cliente. / Creates a new invoice for the client specified by the client ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Factura creada / Invoice created"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta / Bad request")
    })
    @PostMapping(path = "/create/client/{clientId}")
    public ResponseEntity<InvoiceDTO> create(@PathVariable Long clientId) {
        try {
            Optional<InvoiceDTO> invoice = invoiceService.createInvoice(clientId);

            return ResponseEntity.status(HttpStatus.CREATED).body(invoice.get());
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Obtener facturas por cliente / Get invoices by client",
            description = "Recupera todas las facturas asociadas al cliente especificado por el ID del cliente. / Retrieves all invoices associated with the client specified by the client ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facturas encontradas / Invoices found"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado / Client not found"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta / Bad request")
    })
    @GetMapping(path = "/client/{clientId}")
    public ResponseEntity<List<InvoiceDTO>> findByClient(@PathVariable Long clientId) {
        try {
            Optional<List<InvoiceDTO>> invoices = invoiceService.findByClient(clientId);

            if (!invoices.isPresent()) {

                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(invoices.get());
        } catch (Exception e) {

            return ResponseEntity.badRequest().build();
        }
    }
}