package com.snapbox.catalog_service.services;

import com.snapbox.catalog_service.dtos.AddProductRequest;
import com.snapbox.catalog_service.dtos.ProductDto;
import com.snapbox.catalog_service.exceptions.EntityNotFoundException;
import com.snapbox.catalog_service.models.Brand;
import com.snapbox.catalog_service.models.Category;
import com.snapbox.catalog_service.models.Product;
import com.snapbox.catalog_service.models.Seller;
import com.snapbox.catalog_service.repositories.BrandRepository;
import com.snapbox.catalog_service.repositories.CategoryRepository;
import com.snapbox.catalog_service.repositories.ProductRepository;
import com.snapbox.catalog_service.repositories.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private Category category;
    private Brand brand;
    private Seller seller;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        brand = new Brand();
        brand.setId(1L);
        brand.setName("Samsung");

        seller = new Seller();
        seller.setId(1L);
        seller.setName("BestSeller");

        product = new Product();
        product.setId(1L);
        product.setName("Galaxy S24 Ultra");
        product.setDescription("Samsung's flagship.");
        product.setPrice(1000.99);
        product.setCategory(category);
        product.setBrand(brand);
        product.setSeller(seller);
    }

    @Test
    void testGetProductsByCategory() {
        when(categoryRepository.existsById(1L)).thenReturn(true);
        List<Product> products = new ArrayList<>();
        products.add(product);
        when(productRepository.findByCategoryId(1L)).thenReturn(products);

        List<ProductDto> result = productService.getProductsByCategory(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Galaxy S24 Ultra", result.get(0).getName());
    }

    @Test
    void testGetProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductDto result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Galaxy S24 Ultra", result.getName());
    }

    @Test
    void testAddProduct() {
        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setName("New Product");
        addProductRequest.setDescription("New Product Description");
        addProductRequest.setPrice(500.0);
        addProductRequest.setBrandId(1L);
        addProductRequest.setCategoryId(1L);
        addProductRequest.setSellerId(1L);

        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));

        productService.addProduct(addProductRequest);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testAddProductThrowsEntityNotFoundExceptionForCategory() {
        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setBrandId(1L);
        addProductRequest.setCategoryId(1L);
        addProductRequest.setSellerId(1L);

        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            productService.addProduct(addProductRequest);
        });

        assertEquals("Category not found. Category ID: 1", exception.getMessage());
    }

    @Test
    void testAddProductThrowsEntityNotFoundExceptionForSeller() {
        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setBrandId(1L);
        addProductRequest.setCategoryId(1L);
        addProductRequest.setSellerId(1L);

        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(sellerRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            productService.addProduct(addProductRequest);
        });

        assertEquals("Seller not found. Seller ID: 1", exception.getMessage());
    }

    @Test
    void testAddProductThrowsEntityNotFoundExceptionForBrand() {
        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setBrandId(1L);
        addProductRequest.setCategoryId(1L);
        addProductRequest.setSellerId(1L);

        when(brandRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            productService.addProduct(addProductRequest);
        });

        assertEquals("Brand not found. Brand ID: 1", exception.getMessage());
    }

}