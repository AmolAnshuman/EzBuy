package com.ecommerce.EzBuy.service;

import com.ecommerce.EzBuy.exceptions.ResourceNotFoundException;
import com.ecommerce.EzBuy.model.Cart;
import com.ecommerce.EzBuy.model.Product;
import com.ecommerce.EzBuy.payload.CartDTO;
import com.ecommerce.EzBuy.repositories.CartRepository;
import com.ecommerce.EzBuy.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    CartRepository cartRepository;

    @Autowired
    AuthUtil authUtil;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        Cart cart = createCart();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        

        return null;
    }

    private Cart createCart() {
        Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        if(userCart != null) {
            return userCart;
        }
        Cart cart = new Cart();
        cart.setTotalPrice(0.00);
        cart.setUser(authUtil.loggedInUser());
        Cart newCart = cartRepository.save(cart);
        return newCart;
    }
}
