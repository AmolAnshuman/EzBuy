package com.ecommerce.EzBuy.service;

import com.ecommerce.EzBuy.exceptions.APIException;
import com.ecommerce.EzBuy.exceptions.ResourceNotFoundException;
import com.ecommerce.EzBuy.model.Category;
import com.ecommerce.EzBuy.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{


    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        if(categoryRepository.findAll().isEmpty()) {
            throw new APIException("No Category found");
        }
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(savedCategory != null) {
            throw new APIException(String.format("Category %s already exists ", category.getCategoryName()));
        }
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("category","categoryId",categoryId));
        categoryRepository.delete(category);
        return "Category with category id " + categoryId + " deleted successfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {


        Category savedCategory = categoryRepository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("category","categoryId",categoryId));
        Category existingCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(existingCategory != null) {
            throw new APIException(String.format("Category %s already exists ", category.getCategoryName()));
        }
        category.setCategoryId(categoryId);
        existingCategory = categoryRepository.save(category);
        return existingCategory;
    }
}
