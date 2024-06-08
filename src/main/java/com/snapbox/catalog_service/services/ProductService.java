package com.snapbox.catalog_service.services;

import com.snapbox.catalog_service.models.Product;
import com.snapbox.catalog_service.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getProductsByCategory(Long categoryId){
        List<Product> productByCategoryList = productRepository.findByCategoryId(categoryId);
        if(productByCategoryList.isEmpty()){
            throw new RuntimeException("Either the category does not exist or no products under category id: "+ categoryId);
        }
        return productByCategoryList;
    }

    public Product getProductById(Long productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isEmpty()){
            throw new RuntimeException("No product matching given product id: " + productId);
        }
        return optionalProduct.get();
    }
}
