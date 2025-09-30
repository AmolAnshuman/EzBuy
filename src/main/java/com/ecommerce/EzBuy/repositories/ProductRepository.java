package com.ecommerce.EzBuy.repositories;

import com.ecommerce.EzBuy.model.Category;
import com.ecommerce.EzBuy.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryOrderByPriceAsc(Category category); //JPA is going to automatically generate the query based on this method.

    List<Product> findByProductNameLikeIgnoreCase(String keyword); //JPA is going to automatically generate the query based on this method.
}
