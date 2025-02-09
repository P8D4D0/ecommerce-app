package com.product.controller;

import com.product.entities.Product;
import com.product.repositories.ProductRepository;
import com.product.service.ProductService;
import com.product.serviceimpl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService service;

//    @Autowired
//    private ProductRepository repo;


    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts(){

        return service.getAllProducts();
    }

    @GetMapping("/getById/{id}")
    public Product getProductById(@PathVariable Long id){

        return service.getProductById(id);
    }

    @PostMapping("/addProduct")
    public String saveProduct(@RequestBody Product product){
        return service.addProduct(product);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id){
        return service.deleteProduct(id);
    }
}
