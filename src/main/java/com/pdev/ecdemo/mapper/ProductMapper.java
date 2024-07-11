package com.pdev.ecdemo.mapper;

import com.pdev.ecdemo.dto.ProductDTO;
import com.pdev.ecdemo.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends _GenericMapper<Product, ProductDTO> {

    @Override
    public ProductDTO toDTO(Product product) {

        System.out.println("Mapping Product ID: " + product.getId());

        return new ProductDTO(
                product.getId(),
                product.getBrand(),
                product.getDescription(),
                product.getCode(),
                product.getStock(),
                product.getPrice()
        );
    }

    @Override
    public Product toEntity(ProductDTO productDTO) {

        return new Product(
                productDTO.getBrand(),
                productDTO.getDescription(),
                productDTO.getCode(),
                productDTO.getStock(),
                productDTO.getPrice()
        );
    }

}
