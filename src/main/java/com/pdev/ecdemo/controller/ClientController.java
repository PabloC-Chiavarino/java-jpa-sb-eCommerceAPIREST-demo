package com.pdev.ecdemo.controller;

import com.pdev.ecdemo.dto.ClientDTO;
import com.pdev.ecdemo.entity.Client;
import com.pdev.ecdemo.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/clients")
@Tag(name = "Cliente / Client", description = "Operaciones específicas para clientes / Specific operations for clients")
public class ClientController extends _GenericController<Client, ClientDTO> {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService service) {
        super(service);
        this.clientService = service;
    }

    @Operation(
            summary = "Obtener un cliente por ID / Get a client by ID",
            description = "Recupera un cliente por su ID único. / Retrieves a client by their unique ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado / Client found"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado / Client not found")
    })
    @Override
    @GetMapping(path = "/{id}")
    public ResponseEntity<ClientDTO> getById(@PathVariable Long id) {

        return super.getById(id);
    }

    @Operation(
            summary = "Obtener un cliente por número de documento o pasaporte / Get a client by document or passport number",
            description = "Recupera un cliente por su número de documento. / Retrieves a client by their document number."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado / Client found"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado / Client not found"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta / Bad request")
    })
    @GetMapping("/doc_pass/{docPass}")
    public ResponseEntity<ClientDTO> getByDocPass(@PathVariable Long docPass) {
        try {
            Optional<ClientDTO> client = clientService.findByDocPass(docPass);
            if (!client.isPresent()) {

                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(client.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Obtener un cliente por apellido / Get a client by last name",
            description = "Recupera un cliente por su apellido. / Retrieves a client by their last name."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado / Client found"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado / Client not found"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta / Bad request")
    })
    @GetMapping("/last_name/{lastName}")
    public ResponseEntity<ClientDTO> getByLastName(@PathVariable String lastName) {
        try {
            Optional<ClientDTO> client = clientService.findByLastName(lastName);
            if (!client.isPresent()) {

                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(client.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return ResponseEntity.badRequest().build();
        }
    }
}
