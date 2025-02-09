package com.order.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orderItems")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

//    @Column(insertable=false, updatable=false)
//    private Long orderId;

    private Long productId;

    private Integer quantity;

    private double price;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;

    public Long getOrderItemId() {
        return orderItemId;
    }


    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }



    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
