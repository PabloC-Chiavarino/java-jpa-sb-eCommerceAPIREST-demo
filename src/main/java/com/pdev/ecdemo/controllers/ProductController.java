package com.pdev.ecdemo.controllers;

import com.pdev.ecdemo.entities.Product;
import com.pdev.ecdemo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController @RequestMapping(path = "/api/v1/products")
public class ProductController extends _GenericController<Product> {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService service) {
        super(service);
        this.productService = service;
    }


    @GetMapping("/code/{code}")
    public ResponseEntity<Product> getByCode(@PathVariable Long code) {
        try {
            Optional<Product> product = productService.findByCode(code);
            if (!product.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(product.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/description/{description}")
    public ResponseEntity<Product> getByDescription(@PathVariable String description) {
        try {
            Optional<Product> product = productService.findByDescription(description);
            if (!product.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(product.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<Product> getByBrand(@PathVariable String brand) {
        try {
            Optional<Product> product = productService.findByBrand(brand);
            if (!product.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(product.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}