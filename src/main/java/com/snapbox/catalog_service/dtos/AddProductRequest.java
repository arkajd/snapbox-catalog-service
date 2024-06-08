package com.snapbox.catalog_service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProductRequest {

    private String name;
    private String description;
    private Double price;
    private Long brandId;
    private Long categoryId;
    private Long sellerId;
}
