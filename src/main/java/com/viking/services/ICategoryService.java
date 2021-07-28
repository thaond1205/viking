package com.viking.services;

import com.viking.dto.CategoriesDTO;
import com.viking.entities.Category;

import java.util.List;

public interface ICategoryService {
    Category findCategoryById(String id);

    List<Category> findAll();

    Category createCategory(Category category);

    Category updateCategory(CategoriesDTO category);
}
