package com.ecommerce.EzBuy.service;

import com.ecommerce.EzBuy.payload.CartDTO;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity) ;
}
