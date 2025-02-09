package com.cart.repositories;

import com.cart.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartReposotory extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserId(Long userId);

}
