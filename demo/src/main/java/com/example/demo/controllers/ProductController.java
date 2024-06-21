package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdminDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.models.Admin;
import com.example.demo.models.Product;
import com.example.demo.services.impl.AdminServiceImpl;
import com.example.demo.services.impl.ProductServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/products")
@CrossOrigin(origins="*")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private AdminServiceImpl adminService;

    @PostMapping("/{adminId}")
    public ProductDTO createProduct(@PathVariable Long adminId, @RequestBody Product product) {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName(product.getProductName());
        productDTO.setProductID(product.getProductID());
        productDTO.setPrice(product.getPrice());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setReleaseDate(product.getReleaseDate());
        productDTO.setAvailabilityDate(product.getAvailabilityDate());

        product.setAdmin(adminService.getAdmin(adminId));

        productService.createProduct(product);

        return productDTO;

        
    }
    
    @GetMapping
    public String getProducts() {

        ObjectMapper objectMapper = new ObjectMapper();

        String products = "";

        List<Product> productList = productService.getProducts();

       for(Product p: productList){
        if(p.getQuantity() > 0){
          ProductDTO productDTO = new ProductDTO();

            productDTO.setProductName(p.getProductName());
            productDTO.setProductID(p.getProductID());
            productDTO.setPrice(p.getPrice());
            productDTO.setQuantity(p.getQuantity());
            productDTO.setReleaseDate(p.getReleaseDate());
            productDTO.setAvailabilityDate(p.getAvailabilityDate());

            String json;
            try {
                json = objectMapper.writeValueAsString(productDTO);
                products += json+"\n";
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
       }

        return products;
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{productName}")    
    public void deleteProduct(@PathVariable String productName) {
        Product deleted = productService.getProductByProductName(productName);
        deleted.setQuantity(-1);
         // set quantity to -1 to delete but keep the product as historical data
        productService.updateProduct(deleted.getProductID(),deleted); 

    }
    @DeleteMapping("/id/{id}")    
    public void deleteProduct(@PathVariable Long id) {
        Product deleted = productService.getProduct(id);
        deleted.setQuantity(-1);
         // set quantity to -1 to delete but keep the product as historical data
        productService.updateProduct(deleted.getProductID(),deleted); 

    }
}
