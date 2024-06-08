package com.snapbox.catalog_service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddSellerRequest {

    private String name;
    private String description;
}
