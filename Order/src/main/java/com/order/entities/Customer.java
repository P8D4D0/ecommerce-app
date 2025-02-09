//package com.order.entities;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "customers")
//public class Customer {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long customerId;
//
//    private String customerName;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "customer_Id", referencedColumnName = "customerId")
//    private List<Address> addressList;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "order_Id", referencedColumnName = "orderId")
//    private List<Order> orders;
//
//
//
//}
