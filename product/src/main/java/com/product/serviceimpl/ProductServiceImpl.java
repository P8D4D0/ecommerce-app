package com.product.serviceimpl;

import com.product.entities.Product;
import com.product.repositories.ProductRepository;
import com.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repo;

    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Product> getAllProducts() {

        return repo.findAll();
    }

    @Override
    public String addProduct(Product product) {
        repo.save(product);
        return "Product Added Successfully !!!";
    }

    @Override
    public Product getProductById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    @Override
    public String updateProduct(Product product) {
        return "";
    }

    @Override
    public String deleteProduct(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Product not found with ID: " + id);
        }
        repo.deleteById(id);
        return "Deleted !";
    }

    @Override
    public List<Product> getProductsByCategory() {
        return repo.findAll();
    }
}
