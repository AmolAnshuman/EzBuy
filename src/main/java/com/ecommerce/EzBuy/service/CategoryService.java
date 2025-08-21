package com.ecommerce.EzBuy.service;

import com.ecommerce.EzBuy.model.Category;
import com.ecommerce.EzBuy.payload.CategoryDTO;
import com.ecommerce.EzBuy.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
