package com.ecommerce.EzBuy.repositories;

import com.ecommerce.EzBuy.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
