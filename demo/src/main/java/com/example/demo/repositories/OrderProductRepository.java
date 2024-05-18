package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.models.OrderProduct;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    // BEGIN: be15d9bcejpp
    @Query("SELECT op FROM OrderProduct op WHERE op.order.orderID = :orderID")
    List<OrderProduct> findByOrderId(Long orderID);


    
}

  


    

