package com.snapbox.catalog_service.controllers;

import com.snapbox.catalog_service.dtos.AddProductRequest;
import com.snapbox.catalog_service.dtos.ProductDto;
import com.snapbox.catalog_service.models.Product;
import com.snapbox.catalog_service.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/products/categories/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable Long categoryId){
        List<ProductDto> productList = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId){
        ProductDto productDto = productService.getProductById(productId);
        return ResponseEntity.ok(productDto);
    }

    @PostMapping("/products")
    public void addProduct(@RequestBody AddProductRequest addProductRequest){
        productService.addProduct(addProductRequest);
    }
}
