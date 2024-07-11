package com.pdev.ecdemo.controllers;

import com.pdev.ecdemo.entities.Client;
import com.pdev.ecdemo.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController @RequestMapping(path = "/api/v1/clients")
public class ClientController extends _GenericController<Client> {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService service) {
        super(service);
        this.clientService = service;
    }


    /*@GetMapping("/all")
    public ResponseEntity<List<Client>> findAll() {
        try {
            List<Client> clients = clientService.findAll();
            return ResponseEntity.ok(clients);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }*/

    /*@PostMapping("/create")
    public ResponseEntity<Client> create(@RequestBody @Valid Client data) {
        try {
            Client client = clientService.saveClient(data);
            return ResponseEntity.status(HttpStatus.CREATED).body(client);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }*/

    /*@PutMapping("/update/{id}")
    public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody @Valid Client data) {
        try {
            Optional<Client> client = clientService.updateClient(id, data);
            if (client.isPresent()) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(client.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }*/

    /*@DeleteMapping("/delete/{id}")
    public ResponseEntity<Client> delete(@PathVariable @RequestBody @Valid Long id) {
        try {
            Optional<Client> client = clientService.deleteClient(id);
            if(client.isPresent()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }**/

    /*@GetMapping("/client/{id}")
    public ResponseEntity<Client> findOne(@PathVariable @NonNull Long id) {
        try {
            Optional<Client> client = clientService.findById(id);
            if (client.isPresent()) {
                return ResponseEntity.ok(client.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }*/

    @GetMapping("/doc_pass/{docPass}")
    public ResponseEntity<Client> getByDocPass(@PathVariable Long docPass) {
        try {
            Optional<Client> client = clientService.findByDocPass(docPass);
            if (!client.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(client.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/last_name/{lastName}")
    public ResponseEntity<Client> getByLastName(@PathVariable String lastName) {
        try {
            Optional<Client> client = clientService.findByLastName(lastName);
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
