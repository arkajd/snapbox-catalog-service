package com.snapbox.catalog_service.mappers;

import com.snapbox.catalog_service.dtos.ProductDto;
import com.snapbox.catalog_service.models.Product;

public class ProductMapper {

    public static ProductDto toDto(Product product){
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setBrand(product.getBrand());
        dto.setCategory(product.getCategory());
        dto.setSeller(product.getSeller());
        return dto;
    }

}
