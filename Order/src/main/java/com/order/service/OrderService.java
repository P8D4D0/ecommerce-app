package com.order.service;

import com.order.dto.CartDto;

public interface OrderService {

    public String placeOrder(Long userId);

    public CartDto getCartByUserID(Long userId);
}
