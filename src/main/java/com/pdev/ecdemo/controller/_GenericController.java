package com.pdev.ecdemo.controller;

import com.pdev.ecdemo.dto._GenericDTO;
import com.pdev.ecdemo.service._GenericService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/{entity}")
@Tag(name = "CRUD Genérico", description = "Operaciones CRUD genéricas para cualquier entidad / Generic CRUD operations for any entity")
public class _GenericController<T, DTO extends _GenericDTO> {

    @Autowired
    protected final _GenericService<T, DTO> service;

    @Operation(
            summary = "Obtener todos los elementos / Get all entities",
            description = "Recupera una lista de todos los elementos disponibles. / Retrieves a list of all available entities."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de elementos encontrada / List of entities found"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta / Bad request")
    })
    @GetMapping
    public ResponseEntity<List<DTO>> getAll() {
        try {
            List<DTO> entityList = service.getEntityList();

            return ResponseEntity.ok(entityList);
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Crear un nuevo elemento / Create a new entity",
            description = "Crea un nuevo elemento con los datos proporcionados. / Creates a new entity with the provided data."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Elemento creado / Entity created"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta / Bad request")
    })
    @PostMapping(path = "/create")
    public ResponseEntity<DTO> create(@RequestBody @Valid T data) {
        try {
            DTO entity = service.saveEntity(data);

            return ResponseEntity.status(HttpStatus.CREATED).body(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Actualizar un elemento (solo clientes y productos) / Update an entity (only clients and products)",
            description = "Actualiza un elemento existente con el ID y los datos proporcionados. / Updates an existing entity with the provided ID and data."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Elemento actualizado / Entity updated"),
            @ApiResponse(responseCode = "404", description = "Elemento no encontrado / Entity not found"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta / Bad request")
    })
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<DTO> update(@PathVariable Long id, @RequestBody @Valid DTO data) {
        try {
            Optional<DTO> updatedEntity = service.updateEntity(id, data);
            if (!updatedEntity.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedEntity.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Eliminar un elemento / Delete an entity",
            description = "Elimina un elemento existente con el ID proporcionado. / Deletes an existing entity with the provided ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Elemento eliminado / Entity deleted"),
            @ApiResponse(responseCode = "404", description = "Elemento no encontrado / Entity not found"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta / Bad request")
    })
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<DTO> delete(@PathVariable @Valid Long id) {
        try {
            Optional<DTO> deletedEntity = service.deleteEntity(id);
            if (!deletedEntity.isPresent()) {

                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Obtener un elemento por ID / Get an entity by ID",
            description = "Recupera un elemento existente con el ID proporcionado. / Retrieves an existing entity with the provided ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Elemento encontrado / Entity found"),
            @ApiResponse(responseCode = "404", description = "Elemento no encontrado / Entity not found"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta / Bad request")
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<DTO> getById(@Valid @PathVariable Long id) {
        try {
            Optional<DTO> entity = service.getEntityById(id);
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
