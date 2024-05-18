package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Admin;
import com.example.demo.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.productName = :productName")
    Product findByProductName(@Param("productName") String productName);

    @Query("SELECT p FROM Product p WHERE p.productID = :productID")
    Product findByProductID(@Param("productID") Long productID);
    
    @Query("SELECT p FROM Product p WHERE p.admin = :admin")
    List<Product> findByAdmin(@Param("admin") Admin admin);


    @Query("SELECT p.admin FROM Product p WHERE p = :product")
    Admin findAdminByProduct(@Param("product") Product product);

    
    @Modifying
    @Query("DELETE FROM Product p WHERE p.productName = :productName")
    void deleteByProductName(@Param("productName") String productName);

    @Modifying
    @Query("UPDATE Product p SET p.quantity = p.quantity - :quantity WHERE p.productName = :productName")
    Integer decreaseQuantityByProductName(@Param("productName") String productName, @Param("quantity") int quantity);
    
    @Query(value = "SELECT jsonb_agg(jsonb_build_object('ID', p.productid, 'product name', p.product_name, 'quantity', p.quantity, 'realease date' , p.release_date, 'createdBy', p.admin_id)) FROM Products p;", nativeQuery = true)
    String findAllProductsJson();
    
}