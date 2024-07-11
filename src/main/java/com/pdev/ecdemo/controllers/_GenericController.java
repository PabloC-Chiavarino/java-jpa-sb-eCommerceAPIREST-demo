package com.pdev.ecdemo.controllers;

import com.pdev.ecdemo.services._GenericService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

    @AllArgsConstructor @RestController @RequestMapping(path = "/api/v1/{entity}")
    public class _GenericController<T> {

        @Autowired
        protected final _GenericService<T> service;

    @GetMapping
    public ResponseEntity<List<T>> getAll() {
        try {
            List<T> entityList = service.getEntityList();
            return ResponseEntity.ok(entityList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "/create")
    public ResponseEntity<T> create(@RequestBody @Valid T data) {
        try {
            T entity = service.saveEntity(data);
            return ResponseEntity.status(HttpStatus.CREATED).body(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /*@PutMapping(path = "/update/{id}")
    public ResponseEntity<T> update(@PathVariable Long id, @RequestBody @Valid T data) {
        try {
            Optional<T> updatedEntity = service.updateEntity(id, data);
            if (!updatedEntity.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedEntity.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }*/

    @PostMapping(path = "/delete/{id}")
    public ResponseEntity<T> delete(@PathVariable @Valid Long id) {
        try {
            Optional<T> deletedEntity = service.deleteEntity(id);
            if (!deletedEntity.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<T> getById(@Valid @PathVariable Long id) {
        try {
            Optional<T> entity = service.getEntityById(id);
            if (!entity.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(entity.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
