package com.pdev.ecdemo.service;

import com.pdev.ecdemo.dto.ProductDTO;
import com.pdev.ecdemo.entity.Product;
import com.pdev.ecdemo.mapper.ProductMapper;
import com.pdev.ecdemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService extends _GenericService<Product, ProductDTO> {

    private final String found = "Product found";
    private final String notFound = "Product not found";

    private final ProductMapper productMapper;
    private final _DataProviderService dataProvider;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper, _DataProviderService dataProvider) {
        super(productRepository, productMapper);
        this.productMapper = productMapper;
        this.dataProvider = dataProvider;
    }

    public Optional<ProductDTO> findByCode(Long code) {

        Optional<Product> product = dataProvider.getProductByCode(code);
        if (product.isPresent()) {
            System.out.println(found);

            ProductDTO productDTO = productMapper.toDTO(product.get());

            return Optional.of(productDTO);
        } else {
            System.out.println(notFound);

            return Optional.empty();
        }
    }

    public Optional<ProductDTO> findByDescription(String description) {

        Optional<Product> product = dataProvider.getProductByDescription(description);
        if (product.isPresent()) {
            System.out.println(found);

            ProductDTO productDTO = productMapper.toDTO(product.get());

            return Optional.of(productDTO);
        } else {
            System.out.println(notFound);

            return Optional.empty();
        }
    }

    public Optional<ProductDTO> findByBrand(String brand) {

        Optional<Product> product = dataProvider.getProductByBrand(brand);

        if (product.isPresent()) {
            System.out.println(found);

            ProductDTO productDTO = productMapper.toDTO(product.get());

            return Optional.of(productDTO);
        } else {
            System.out.println(notFound);

            return Optional.empty();
        }
    }
}
