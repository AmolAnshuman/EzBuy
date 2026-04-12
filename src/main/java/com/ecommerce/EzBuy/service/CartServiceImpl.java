package com.ecommerce.EzBuy.service;

import com.ecommerce.EzBuy.exceptions.APIException;
import com.ecommerce.EzBuy.exceptions.ResourceNotFoundException;
import com.ecommerce.EzBuy.model.Cart;
import com.ecommerce.EzBuy.model.CartItem;
import com.ecommerce.EzBuy.model.Product;
import com.ecommerce.EzBuy.payload.CartDTO;
import com.ecommerce.EzBuy.payload.ProductDTO;
import com.ecommerce.EzBuy.repositories.CartItemRepository;
import com.ecommerce.EzBuy.repositories.CartRepository;
import com.ecommerce.EzBuy.repositories.ProductRepository;
import com.ecommerce.EzBuy.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        Cart cart = createCart();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cart.getCartId(),productId);

        if(cartItem != null){
            throw new APIException("Prodcut " + product.getProductName() + " already exists");
        }

        if(product.getQuantity() == 0){
            throw new APIException(product.getProductName() + " is not in stock");
        }
        if(product.getQuantity() < quantity){
            throw new APIException("Please enter less than or equal to " + product.getQuantity() + "quantity");
        }
        CartItem newCartItem = new CartItem();
        newCartItem.setCart(cart);
        newCartItem.setProduct(product);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getPrice());

        cartItemRepository.save(newCartItem);
        //product.setQuantity(product.getQuantity() - quantity);
        cart.setTotalPrice(cart.getTotalPrice() + product.getSpecialPrice()*quantity);
        cartRepository.save(cart);

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        List<CartItem> cartItems = cart.getCartItems();

        Stream<ProductDTO> productDTOStream = cartItems.stream().map(item ->
        { ProductDTO map = modelMapper.map(item.getProduct(), ProductDTO.class);
        map.setQuantity(item.getQuantity());
        return map;});

        cartDTO.setProducts(productDTOStream.collect(Collectors.toList()));

        return cartDTO;
    }

    @Override
    public List<CartDTO> getAllCarts() {
        List<Cart>carts = cartRepository.findAll();
        if(carts.size() == 0){
            throw new APIException("no cart exists");
        }
        List<CartDTO> cartDTOs = carts.stream().map(cart -> {
            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
            List<ProductDTO> products = cart.getCartItems().stream()
                    .map(p -> modelMapper.map(p.getProduct(),ProductDTO.class))
                    .collect(Collectors.toList());
            cartDTO.setProducts(products);
            return cartDTO;
        }).collect(Collectors.toList());
        return cartDTOs;
    }

    @Override
    public CartDTO getCart(String emailId, Long cartId) {
        Cart cart = cartRepository.findCartByEmailAndCartId(emailId,cartId);
        if(cart == null){
            throw new ResourceNotFoundException("Cart", "cartId", cartId);
        }
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cart.getCartItems().forEach(c -> c.getProduct().setQuantity(c.getQuantity()));
        List<ProductDTO> products = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProduct(),ProductDTO.class))
                .collect(Collectors.toList());
        cartDTO.setProducts(products);
        return cartDTO;
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
