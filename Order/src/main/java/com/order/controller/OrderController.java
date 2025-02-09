package com.order.controller;

import com.order.dto.CartDto;
import com.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping("placeOrder/{userId}")
    public String placeOrder(@PathVariable Long userId){
        return service.placeOrder(userId);
    }

    @GetMapping("getCart/user/{userId}")
    public CartDto getCartByUserId(@PathVariable Long userId){
        return service.getCartByUserID(userId);
    }
}
