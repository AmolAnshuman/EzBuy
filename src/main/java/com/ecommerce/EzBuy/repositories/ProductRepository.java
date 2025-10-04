package com.ecommerce.EzBuy.repositories;

import com.ecommerce.EzBuy.model.Category;
import com.ecommerce.EzBuy.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategoryOrderByPriceAsc(Category category, Pageable pageDetails); //JPA is going to automatically generate the query based on this method.

    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageDetails); //JPA is going to automatically generate the query based on this method.
}
