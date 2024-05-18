package com.example.demo.services;

import java.util.List;

import com.example.demo.models.Product;

public interface ProductService {
    
    public Product createProduct(Product product);
    public Product getProduct(Long id);
    public Product getProductByProductName(String productName);
    public Product updateProduct(Long id, Product product);
    public boolean deleteProduct(String productName);
    public boolean deleteProduct(String productName, int quantity);
    public List<Product> getProducts();

}
