package com.viking.services.impl;

import com.viking.dto.CategoriesDTO;
import com.viking.entities.Category;
import com.viking.repositories.CategoryRepository;
import com.viking.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category findCategoryById(String id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(CategoriesDTO entity) {
        Category category = categoryRepository.findById(entity.getId()).get();
        category.setName(entity.getName());

        return categoryRepository.save(category);
    }
}
