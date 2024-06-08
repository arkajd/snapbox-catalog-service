package com.snapbox.catalog_service.dtos;

import com.snapbox.catalog_service.models.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Brand brand;
    private Category category;
    private Seller seller;
    private List<ProductImage> productImageList;
    private List<ProductReview> productReviewList;
}
