package com.pdev.ecdemo.service;

import com.pdev.ecdemo.dto._GenericDTO;
import com.pdev.ecdemo.mapper._GenericMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Primary
public class _GenericService<T, DTO extends _GenericDTO> {

    @Autowired
    private final JpaRepository<T, Long> repository;
    private final _GenericMapper<T, DTO> mapper;

    public DTO saveEntity(T entity) {

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withIgnorePaths("id");

        Example<T> example = Example.of(entity, matcher);
        Optional<T> entityCheck = repository.findOne(example);

        if (entityCheck.isPresent()) {
            System.out.println("Entity already exists");
            DTO entityDTO = mapper.toDTO(entityCheck.get());

            return entityDTO;
        }

        try {
            T savedEntity = repository.save(entity);

            return mapper.toDTO(savedEntity);

        } catch (Exception e) {
            System.out.println("Error saving entity: " + e.getMessage());

            throw new RuntimeException("Error saving entity");
        }
    }

    public Optional<DTO> updateEntity(Long id, DTO entity) {
        Optional<T> entityCheck = repository.findById(id);
        if (!entityCheck.isPresent()) {
            System.out.println("Entity not found");

            return Optional.empty();
        }
        T existingEntity = entityCheck.get();
        BeanUtils.copyProperties(entity, existingEntity, "id", "invoices", "orders");

        return Optional.of(mapper.toDTO(repository.save(existingEntity)));
    }

    public Optional<DTO> deleteEntity(Long id) {
        Optional<T> entityCheck = repository.findById(id);
        if (!entityCheck.isPresent()) {
            System.out.println("Entity not found");

            return Optional.empty();
        }
        repository.deleteById(id);
        System.out.println("Entity deleted");

        return Optional.of(mapper.toDTO(entityCheck.get()));
    }

    public Optional<DTO> getEntityById(Long id) {
        Optional<T> entityCheck = repository.findById(id);

        if (!entityCheck.isPresent()) {
            System.out.println("Entity not found");

            return Optional.empty();
        }
        System.out.println("Entity found");
        entityCheck.get();

        return Optional.of(mapper.toDTO(entityCheck.get()));
    }

    public List<DTO> getEntityList() {
        System.out.println("Getting entity list...");

        return mapper.toDTOList(repository.findAll());
    }
}

