package com.snapbox.catalog_service.services;

import com.snapbox.catalog_service.dtos.AddProductRequest;
import com.snapbox.catalog_service.dtos.ProductDto;
import com.snapbox.catalog_service.exceptions.EmptyCategoryException;
import com.snapbox.catalog_service.exceptions.EntityNotFoundException;
import com.snapbox.catalog_service.mappers.ProductMapper;
import com.snapbox.catalog_service.models.Brand;
import com.snapbox.catalog_service.models.Category;
import com.snapbox.catalog_service.models.Product;
import com.snapbox.catalog_service.models.Seller;
import com.snapbox.catalog_service.repositories.BrandRepository;
import com.snapbox.catalog_service.repositories.CategoryRepository;
import com.snapbox.catalog_service.repositories.ProductRepository;
import com.snapbox.catalog_service.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final SellerRepository sellerRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          BrandRepository brandRepository,
                          SellerRepository sellerRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.sellerRepository = sellerRepository;
    }

    public List<ProductDto> getProductsByCategory(Long categoryId){
        boolean categoryExists = categoryRepository.existsById(categoryId);

        if(!categoryExists){
            throw new EntityNotFoundException("Category not found. Category ID: " + categoryId);
        }
        List<Product> productByCategoryList = productRepository.findByCategoryId(categoryId);

        if(productByCategoryList.isEmpty()){
            throw new EmptyCategoryException("No products under provided category. Category ID: " + categoryId);
        }

        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product: productByCategoryList){
            productDtoList.add(ProductMapper.toDto(product));
        }
        return productDtoList;
    }

    public ProductDto getProductById(Long productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isEmpty()){
            throw new EntityNotFoundException("Product not found. Product ID: " + productId);
        }
        return ProductMapper.toDto(optionalProduct.get());
    }

    public void addProduct(AddProductRequest addProductRequest){
        Brand brand = null;
        Seller seller = null;
        Category category = null;

        if(addProductRequest.getBrandId() != null){
            brand = brandRepository.findById(addProductRequest.getBrandId())
                    .orElseThrow(()->new EntityNotFoundException("Brand not found. Brand ID: " + addProductRequest.getBrandId()));
        }
        if(addProductRequest.getCategoryId() != null){
            category = categoryRepository.findById(addProductRequest.getCategoryId())
                    .orElseThrow(()->new EntityNotFoundException("Category not found. Category ID: " + addProductRequest.getCategoryId()));
        }
        if(addProductRequest.getSellerId() != null){
            seller = sellerRepository.findById(addProductRequest.getSellerId())
                    .orElseThrow(()->new EntityNotFoundException("Seller not found. Seller ID: " + addProductRequest.getSellerId()));
        }
        Product product = new Product();
        product.setName(addProductRequest.getName());
        product.setDescription(addProductRequest.getDescription());
        product.setPrice(addProductRequest.getPrice());
        product.setBrand(brand);
        product.setSeller(seller);
        product.setCategory(category);
        productRepository.save(product);
    }
}
