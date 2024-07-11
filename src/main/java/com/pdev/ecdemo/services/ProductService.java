package com.pdev.ecdemo.services;

import com.pdev.ecdemo.entities.Product;
import com.pdev.ecdemo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService extends _GenericService<Product> {

    private final String found = "Product found";
    private final String notFound = "Product not found";

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Optional<Product> findByCode(Long code) {
        Optional<Product> product = repository.findByCode(code);
        if (product.isPresent()) {
            System.out.println(found);
            return product;
        } else {
            System.out.println(notFound);
            return Optional.empty();
        }
    }

    public Optional<Product> findByDescription(String description) {
        Optional<Product> product = repository.findByDescription(description);
        if (product.isPresent()) {
            System.out.println(found);
            return product;
        } else {
            System.out.println(notFound);
            return Optional.empty();
        }
    }

    public Optional<Product> findByBrand(String brand) {
        Optional<Product> product = repository.findByBrand(brand);
        if (product.isPresent()) {
            System.out.println(found);
            return product;
        } else {
            System.out.println(notFound);
            return Optional.empty();
        }
    }
}
