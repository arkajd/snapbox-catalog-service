package com.snapbox.catalog_service.services;

import com.snapbox.catalog_service.dtos.AddCategoryRequest;
import com.snapbox.catalog_service.models.Category;
import com.snapbox.catalog_service.repositories.CategoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public void addCategory(AddCategoryRequest addCategoryRequest){
        Category parentCategory = null;
        if(addCategoryRequest.getParentCategoryId() != null){
            parentCategory = categoryRepository.getById(addCategoryRequest.getParentCategoryId());
        }
        Category category = new Category();
        category.setName(addCategoryRequest.getName());
        category.setDescription(addCategoryRequest.getDescription());
        category.setParentCategory(parentCategory);
        categoryRepository.save(category);
    }

    @PostConstruct
    public void initializeCategories() {
        if (categoryRepository.count() == 0) { // Check if there are no categories yet
            Category electronics = new Category();
            electronics.setName("Electronics");
            electronics.setDescription("All electronic items");

            Category clothing = new Category();
            clothing.setName("Clothing");
            clothing.setDescription("All clothing items");

            categoryRepository.save(electronics);
            categoryRepository.save(clothing);
        }
    }
}
