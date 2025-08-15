package com.ecommerce.EzBuy.Controller;

import com.ecommerce.EzBuy.model.Category;
import com.ecommerce.EzBuy.payload.CategoryDTO;
import com.ecommerce.EzBuy.payload.CategoryResponse;
import com.ecommerce.EzBuy.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //@GetMapping("/api/public/categories") //both RequestMapping and GetMapping can be used
    @RequestMapping(value = "/api/public/categories", method = RequestMethod.GET)
    public ResponseEntity<CategoryResponse> getAllCategories() {
        CategoryResponse categoryResponse  = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }

    @PostMapping("/api/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
    }
    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
            //String status = categoryService.deleteCategory(categoryId);
            CategoryDTO deletedcategoryDTO = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(deletedcategoryDTO, HttpStatus.OK);
    }

    @PutMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId) {

            CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
            return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
    }
}
