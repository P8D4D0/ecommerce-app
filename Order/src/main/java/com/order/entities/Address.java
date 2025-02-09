package com.order.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String fullName;

    private String Country;

    private String state;

    private Long mobileNumber;

    private String buildingName;

    private String locality;

    private String Landmark;

    private String city;

    private Long pincode;





}
