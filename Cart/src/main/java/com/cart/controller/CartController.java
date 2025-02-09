package com.cart.controller;

import com.cart.dto.ProductDto;
import com.cart.entities.Cart;
import com.cart.entities.CartItem;
import com.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CartController {

    @Autowired
    private CartService service;

    @PostMapping("/addToCart/{userId}")
    public Cart addToCart(@PathVariable Long userId, @RequestBody CartItem cartItem){
        return service.addtoCart(userId,cartItem);

    }

    @GetMapping("/getTotal/{userId}")
    public double getTotalAmountByUserId(@PathVariable Long userId){
        return service.getTotalAmountByUserId(userId);
    }


    @GetMapping(value = "/getCart/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
        Optional<Cart> cart = service.getCartByUserId(userId);
        return cart.map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("The cart is empty for this userId"));
    }

    @GetMapping("/getProductByProductId/{productId}")
    public ProductDto getProductByProductId(@PathVariable Long productId){
        return service.getProductByProductId(productId);
    }

    @DeleteMapping("remove/{userId}/{productId}")
    public Cart removeItemFromCart(@PathVariable Long userId, @PathVariable Long productId){
        return service.removeItemFromCart(userId,productId);
    }

    @Transactional
    @DeleteMapping("clearCart/{userId}")
    public void clearCart(@PathVariable Long userId){
        service.clearCart(userId);
    }



}
