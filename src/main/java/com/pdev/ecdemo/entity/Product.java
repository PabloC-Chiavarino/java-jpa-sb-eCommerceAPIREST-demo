package com.pdev.ecdemo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "products")
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Brand cannot be blank")
    private String brand;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Code cannot be blank")
    private Long code;

    @NotNull(message = "Stock cannot be blank")
    private Integer stock;

    @NotNull(message = "Price cannot be blank")
    private Double price;

    public Product(String brand, String description, Long code, Integer stock, Double price) {
        this.brand = brand;
        this.description = description;
        this.code = code;
        this.stock = stock;
        this.price = price;
    }
}
