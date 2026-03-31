package com.ecommerce.EzBuy.repositories;

import com.ecommerce.EzBuy.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {


}
