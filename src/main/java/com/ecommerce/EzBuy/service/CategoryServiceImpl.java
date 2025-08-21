package com.ecommerce.EzBuy.service;

import com.ecommerce.EzBuy.exceptions.APIException;
import com.ecommerce.EzBuy.exceptions.ResourceNotFoundException;
import com.ecommerce.EzBuy.model.Category;
import com.ecommerce.EzBuy.payload.CategoryDTO;
import com.ecommerce.EzBuy.payload.CategoryResponse;
import com.ecommerce.EzBuy.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category> categories = categoryPage.getContent();
        if(categories.isEmpty()) {  //created An API exception when there are no categories present
            throw new APIException("No Category found");
        }
        List<CategoryDTO> categoryDTOS = categories.stream().
                map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        //Created an API exception, while creating category, category already exists. Here we are using findByCategoryName which is not an existing method in repository framework, it is created in Category Repository Interface, we don't need to write the logic for it the JPA takes care of it
        if(savedCategory != null) {
            throw new APIException(String.format("Category %s already exists ", category.getCategoryName()));
        }
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("category","categoryId",categoryId));
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        categoryRepository.delete(category);
        //return "Category with category id " + categoryId + " deleted successfully";
        return categoryDTO;
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category category = modelMapper.map(categoryDTO, Category.class);

        Category savedCategory = categoryRepository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("category","categoryId",categoryId));
        Category existingCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(existingCategory != null) {
            throw new APIException(String.format("Category %s already exists ", category.getCategoryName()));
        }
        category.setCategoryId(categoryId);
        existingCategory = categoryRepository.save(category);
        return modelMapper.map(existingCategory, CategoryDTO.class);
    }
}
