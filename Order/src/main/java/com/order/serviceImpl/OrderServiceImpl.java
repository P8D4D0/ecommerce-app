package com.order.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.dto.CartDto;
import com.order.dto.CartItemDto;
import com.order.entities.Order;
import com.order.entities.OrderItem;
import com.order.repositories.OrderItemRepository;
import com.order.repositories.OrderRepository;
import com.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final RestTemplate restTemplate;

    public OrderServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public OrderItemRepository orderItemRepo;

    @Autowired
    public OrderRepository orderRepo;

    @Transactional
    @Override
    public String placeOrder(Long userId) {

        CartDto cart = getCartByUserID(userId);

        List<CartItemDto> list = cart.getItems();

        if(list.isEmpty()){
            throw new RuntimeException("Cart is Empty");
        }

        Order order =  new Order();
        order.setUserId(userId);

        order.setOrderTotal(getTotalAmountByCart(userId));
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus("Placed");
        order.setShippingAddress("need to implement for address");

        // Convert CartItems to OrderItems and associate them with the Order
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItemDto cdto:list){
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cdto.getProductId());
            orderItem.setQuantity(cdto.getQuantity());
            orderItem.setPrice(cdto.getPrice());
            orderItem.setOrder(order); // Associate with the Order
            orderItems.add(orderItem);
        }

        // Set the OrderItems to the Order
        order.setOrderItems(orderItems);

        // Save the Order (cascades to save OrderItems)
        orderRepo.save(order);


        //clear cart
        String url = "http://localhost:8082/clearCart/"+userId;
        restTemplate.delete(url);
        System.out.println("Cart cleared successfully for userId: " + userId);

        return "Order Placed Successfully !";
    }

    private Double getTotalAmountByCart(Long userId) {
        String url = "http://localhost:8082/getTotal/"+userId;
        return restTemplate.getForObject(url,double.class);


    }


    @Override
    public CartDto getCartByUserID(Long userId){

        String cartServiceUrl = "http://localhost:8082/getCart/" + userId;

        CartDto cart = restTemplate.getForObject(cartServiceUrl, CartDto.class);

        if (cart == null) {
            throw new RuntimeException("Cart not found with ID: " + userId);
        }

        return cart;

    }


}
