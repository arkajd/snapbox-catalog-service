package com.snapbox.catalog_service.services;

import com.snapbox.catalog_service.dtos.AddBrandRequest;
import com.snapbox.catalog_service.models.Brand;
import com.snapbox.catalog_service.models.Seller;
import com.snapbox.catalog_service.repositories.BrandRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository){
        this.brandRepository = brandRepository;
    }

    public void addBrand(AddBrandRequest addBrandRequest){
        Brand brand = new Brand();
        brand.setName(addBrandRequest.getName());
        brand.setDescription(addBrandRequest.getDescription());
        brandRepository.save(brand);
    }

    @PostConstruct
    public void initializeCategories() {
        if (brandRepository.count() == 0) { // Check if there are no categories yet
            Brand nike = new Brand();
            nike.setName("Nike");
            nike.setDescription("Just Do It!");

            Brand samsung = new Brand();
            samsung.setName("Samsung");
            samsung.setDescription("Best Android Brand");

            brandRepository.save(nike);
            brandRepository.save(samsung);
        }
    }
}
