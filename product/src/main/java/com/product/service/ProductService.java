package com.product.service;

import com.product.entities.Product;

import java.util.List;

public interface ProductService {

    public List<Product> getAllProducts();

    public String addProduct(Product product);

    public Product getProductById(Long id);

    public String updateProduct(Product product);

    public String deleteProduct(Long id);

    public List<Product> getProductsByCategory();
}
