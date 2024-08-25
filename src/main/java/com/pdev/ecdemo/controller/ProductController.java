package com.pdev.ecdemo.controller;

import com.pdev.ecdemo.dto.ProductDTO;
import com.pdev.ecdemo.entity.Product;
import com.pdev.ecdemo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/products")
@Tag(name = "Producto / Product", description = "Operaciones específicas para productos / Specific operations for products")
public class ProductController extends _GenericController<Product, ProductDTO> {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService service) {
        super(service);
        this.productService = service;
    }

    @Operation(
            summary = "Obtener un producto por código / Get a product by code",
            description = "Recupera un producto asociado al código especificado. / Retrieves a product associated with the specified code."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado / Product found"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado / Product not found"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta / Bad request")
    })
    @GetMapping("/code/{code}")
    public ResponseEntity<ProductDTO> getByCode(@PathVariable Long code) {
        try {
            Optional<ProductDTO> product = productService.findByCode(code);
            if (!product.isPresent()) {

                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(product.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Obtener un producto por descripción / Get a product by description",
            description = "Recupera un producto asociado a la descripción especificada. / Retrieves a product associated with the specified description."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado / Product found"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado / Product not found"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta / Bad request")
    })
    @GetMapping("/description/{description}")
    public ResponseEntity<ProductDTO> getByDescription(@PathVariable String description) {
        try {
            Optional<ProductDTO> product = productService.findByDescription(description);
            if (!product.isPresent()) {

                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(product.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Obtener un producto por marca / Get a product by brand",
            description = "Recupera un producto asociado a la marca especificada. / Retrieves a product associated with the specified brand."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado / Product found"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado / Product not found"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta / Bad request")
    })
    @GetMapping("/brands/{brand}")
    public ResponseEntity<ProductDTO> getByBrand(@PathVariable String brand) {
        try {
            Optional<ProductDTO> product = productService.findByBrand(brand);
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