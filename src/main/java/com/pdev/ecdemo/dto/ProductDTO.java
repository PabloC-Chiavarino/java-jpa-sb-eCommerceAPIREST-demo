package com.pdev.ecdemo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO extends _GenericDTO {

    private String brand;
    private String description;
    private Long code;
    private Integer stock;
    private Double price;

    public ProductDTO(Long id, String brand, String description, Long code, Integer stock, Double price) {
        super(id);
        this.brand = brand;
        this.description = description;
        this.code = code;
        this.stock = stock;
        this.price = price;
    }

}
