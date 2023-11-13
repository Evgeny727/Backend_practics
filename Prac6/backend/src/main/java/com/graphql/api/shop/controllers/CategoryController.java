package com.graphql.api.shop.controllers;

import com.graphql.api.shop.models.entities.Category;
import com.graphql.api.shop.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Component
@Controller
@CrossOrigin
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @MutationMapping
    public Category createCategory(@Argument Category category) {
        return categoryService.save(category);
    }

    @MutationMapping
    public void deleteCategoryById(@Argument Long id) {
        categoryService.deleteById(id);
    }

    @QueryMapping
    public Category findCategoryById(@Argument Long id) {
        return categoryService.findById(id);
    }

    @QueryMapping
    public List<Category> findAllCategories() {
        return categoryService.findAll();
    }
}
