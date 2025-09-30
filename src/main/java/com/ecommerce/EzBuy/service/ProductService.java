package com.ecommerce.EzBuy.service;

import com.ecommerce.EzBuy.model.Product;
import com.ecommerce.EzBuy.payload.ProductDTO;
import com.ecommerce.EzBuy.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);

    ProductResponse getAllProducts();

    ProductResponse searchByCategory(Long categoryId);

    ProductResponse searchProductByKeyword(String keyword);

    ProductDTO updateProduct(Long productId, Product product);

    ProductDTO deleteProduct(Long productId);
}
