package com.pdev.ecdemo.repositories;

import com.pdev.ecdemo.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public Optional<Product> findByCode(Long code);

    public Optional<Product> findByDescription(String description);

    public Optional<Product> findByBrand(String brand);

}