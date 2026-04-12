package com.ecommerce.EzBuy.service;

import com.ecommerce.EzBuy.payload.CartDTO;

import java.util.List;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity) ;

    List<CartDTO> getAllCarts();
}
