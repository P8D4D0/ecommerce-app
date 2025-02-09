package com.cart.service;

import com.cart.dto.ProductDto;
import com.cart.entities.Cart;
import com.cart.entities.CartItem;

import java.util.Optional;

public interface CartService {

    public Cart addtoCart(Long userId, CartItem cartItem);

    public Optional<Cart> getCartByUserId(Long userId);

    public ProductDto getProductByProductId(Long productId);

    public double getTotalAmountByUserId(Long userId);

    public Cart removeItemFromCart(Long userId, Long productId);

    public void clearCart(Long userID);

}
