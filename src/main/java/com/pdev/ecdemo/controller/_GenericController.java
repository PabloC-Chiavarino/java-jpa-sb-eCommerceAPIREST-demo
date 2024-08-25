    package com.pdev.ecdemo.controller;

    import com.pdev.ecdemo.dto._GenericDTO;
    import com.pdev.ecdemo.service._GenericService;
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
    public class _GenericController<T, DTO extends _GenericDTO> {

        @Autowired
        protected final _GenericService<T, DTO> service;

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
