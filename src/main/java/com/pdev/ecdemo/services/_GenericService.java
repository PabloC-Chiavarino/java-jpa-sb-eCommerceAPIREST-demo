package com.pdev.ecdemo.services;

import lombok.AllArgsConstructor;
//import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.List;
import java.util.Optional;

@Service @AllArgsConstructor
    public class _GenericService<T> {
    
        @Autowired
        private final JpaRepository<T, Long> repository;


        public T saveEntity(T entity) {
            ExampleMatcher matcher = ExampleMatcher
                    .matching()
                    .withIgnoreNullValues()
                    .withIgnoreCase()
                    .withIgnorePaths("id");

            Example<T> example = Example.of(entity, matcher);
            Optional<T> entityCheck = repository.findOne(example);

            if (entityCheck.isPresent()) {
                System.out.println("Entity already exists");
                return entityCheck.get();
            }

            return repository.save(entity);
        }

       /* public Optional<T> updateEntity(Long id, T entity) {
            Optional<T> entityCheck = repository.findById(id);
            if (!entityCheck.isPresent()) {
                System.out.println("Entity not found");
                return Optional.empty();
            }
            T existingEntity = entityCheck.get();
            BeanUtils.copyProperties(entity, existingEntity, "id");
            return Optional.of(repository.save(existingEntity));
        }*/
    
        public Optional<T> deleteEntity(Long id) {
            Optional<T> entityCheck = repository.findById(id);
            if (!entityCheck.isPresent()) {
                System.out.println("Entity not found");
                return Optional.empty();
            }
            repository.deleteById(id);
            System.out.println("Entity deleted");
            return entityCheck;
        }
    
        public Optional<T> getEntityById(Long id) {
            Optional<T> entityCheck = repository.findById(id);
            if (!entityCheck.isPresent()) {
                System.out.println("Entity not found");
                return Optional.empty();
            }
            System.out.println("Entity found");
            entityCheck.get();

            return entityCheck;
        }

        public List<T> getEntityList() {
            System.out.println("Getting entity list...");
            return repository.findAll();
        }


}

