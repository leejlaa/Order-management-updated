package com.example.demo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.demo.models.Admin;
import com.example.demo.models.Customer;
import com.example.demo.models.Product;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
    
    @Autowired // sets the repository to be used by the service in the constructor.
    private ProductRepository productRepository;
    @Autowired
    private AdminRepository adminRepository;

    public ProductServiceImpl(ProductRepository productRepository, AdminRepository adminRepository) {
        this.productRepository = productRepository;
        this.adminRepository = adminRepository;
    }

    public Product createProduct(Product product) {
        Admin admin = adminRepository.findByID(product.getAdmin().getID());

        if (admin == null) {
            return null;
        }
        product.setAdmin(admin);
        
        return productRepository.save(product);
    
        
    }

    public Product findByProductID(Long productID) {
        return productRepository.findByProductID(productID);
    }

    public boolean deleteByProductID(Long productID){
        Product existingProduct = productRepository.findByProductID(productID);
        if (existingProduct != null) {
            productRepository.delete(existingProduct);
            return true;
        }
        return false;
    }

    public String JSONProducts(){
        return productRepository.findAllProductsJson();
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product getProductByProductName(String productName) {
        return productRepository.findByProductName(productName);
    }

    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct != null) {
            existingProduct.setProductName(product.getProductName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setQuantity(product.getQuantity());
            existingProduct.setAvailabilityDate(product.getAvailabilityDate());
            existingProduct.setReleaseDate(product.getReleaseDate());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    public boolean deleteProduct(String productName) {
       Product existingProduct = productRepository.findByProductName(productName);
        if (existingProduct != null) {
            productRepository.delete(existingProduct);
            return true;
        }
        return false;
    }

    public boolean deleteProduct(String productName, int quantity) {
        Product existingProduct = productRepository.findByProductName(productName);
        if (existingProduct != null) {
            if (existingProduct.getQuantity() >= quantity) {
                existingProduct.setQuantity(existingProduct.getQuantity() - quantity);
                productRepository.save(existingProduct);
                return true;
            }
        }
        return false;
       
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }



}
