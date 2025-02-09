package com.cart.serviceImpl;

import com.cart.dto.ProductDto;
import com.cart.entities.Cart;
import com.cart.entities.CartItem;
import com.cart.repositories.CartItemRepository;
import com.cart.repositories.CartReposotory;
import com.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemRepository cartItemRepo;

    @Autowired
    private CartReposotory cartRepo;

    private final RestTemplate restTemplate;

    public CartServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Cart addtoCart(Long userId, CartItem cartItem) {

        ProductDto product = getProductByProductId(cartItem.getProductId());

        if(cartItem.getQuantity()> product.getQuantity()){
            throw new RuntimeException("Not in a stock. Available Stock is "+ product.getQuantity());
        }
        else{
            cartItem.setPrice(product.getPrice());
            cartItem.setProductName(product.getName());
        }

        // Check if the cart exists for the user
        Optional<Cart> optionalCart = cartRepo.findByUserId(userId);

        Cart cart;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            // Create a new cart if none exists
            cart = new Cart();
            cart.setUserId(userId);
            cart.setTotalPrice(0.0);
        }

        // Check if the item already exists in the cart
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(cartItem.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            // Update the quantity and price
            CartItem item = existingItem.get();

            if(item.getQuantity()+cartItem.getQuantity()> product.getQuantity()){
                throw  new RuntimeException("Not in a stock. Available Stock is "+ product.getQuantity());
            }
            else{
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
            }

        // Item.setPrice(item.getPrice() + cartItem.getPrice() * cartItem.getQuantity());
        } else {
            // Add a new item to the cart
            cart.getItems().add(cartItem);
        }

        // Update total price of the cart
        double totalPrice = cart.getItems().stream()
                .mapToDouble(item -> item.getPrice())
                .sum();
        cart.setTotalPrice(totalPrice);

        // Save the cart
        return cartRepo.save(cart);
    }


    public ProductDto getProductByProductId(Long productId){

        String productServiceUrl = "http://localhost:8081/getById/" + productId;

        ProductDto product = restTemplate.getForObject(productServiceUrl, ProductDto.class);

        if (product == null) {
            throw new RuntimeException("Product not found with ID: " + productId);
        }

        return product;

    }

    @Override
    public double getTotalAmountByUserId(Long userId) {

        Optional<Cart> cart = getCartByUserId(userId);

        List<CartItem> items = cart.get().getItems();

        double total=0;
        for(CartItem item:items){
            total+=item.getPrice()*item.getQuantity();
        }

        return total;
    }

    @Override
    public Cart removeItemFromCart(Long userId, Long productId) {

        // Retrieve the cart associated with the userId
        Optional<Cart> cartOptional = getCartByUserId(userId);
        if (cartOptional.isEmpty()) {
            throw new RuntimeException("Cart not found for userId: " + userId);
        }
        Cart cart = cartOptional.get();

        // Filter out the item to be removed
        List<CartItem> updatedItems = cart.getItems()
                .stream()
                .filter(item -> !item.getProductId().equals(productId))
                .collect(Collectors.toList());

        // Update the cart with the new list of items
        cart.setItems(updatedItems);

        // Save the updated cart back to the repository
        cartRepo.save(cart);

        return cart;

    }

    @Override
    public void clearCart(Long userID) {

        Optional<Cart> cartOptional = getCartByUserId(userID);

        if(cartOptional.isPresent()){
            Cart cart=cartOptional.get();

            List<CartItem> items = cartItemRepo.findByItemId(cart.getUserId());
            if(!items.isEmpty()){
                cartItemRepo.deleteByItemId(cart.getUserId());
            }

            cartRepo.delete(cart);
        }
        else{
            throw new RuntimeException("Cart not found for userId "+ userID);
        }


    }


    @Override
    public Optional<Cart> getCartByUserId(Long userId) {
        Optional<Cart> cart = cartRepo.findById(userId);

        if(cart.isPresent()){
            return cart;
        }
        else{
            throw new RuntimeException("The cart is Empty for this userId");
        }

    }



}
