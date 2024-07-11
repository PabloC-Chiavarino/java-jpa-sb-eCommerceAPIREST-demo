package com.pdev.ecdemo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity @Table(name = "products")
@NoArgsConstructor @EqualsAndHashCode @Getter @Setter
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String description;
    private Long code;
    private Integer stock;
    private Double price;

    public Product(String brand, String description, Long code, Integer stock, Double price) {
        this.brand = brand;
        this.description = description;
        this.code = code;
        this.stock = stock;
        this.price = price;
    }

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Order> orders;

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Invoice> invoices;
}
