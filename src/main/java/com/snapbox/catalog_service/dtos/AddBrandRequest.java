package com.snapbox.catalog_service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBrandRequest {

    private String name;
    private String description;
}
