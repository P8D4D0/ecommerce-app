package com.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
//@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
public class CartDto {


//    private Long userId;
//    private double totalPrice;
//    private List<CartItemDto> items = new ArrayList<>();




    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("totalPrice")
    private double totalPrice;
    @JsonProperty("items")
    private List<CartItemDto> items = new ArrayList<>();

    public List<CartItemDto> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }
}
