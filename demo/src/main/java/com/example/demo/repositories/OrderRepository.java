package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.customer.ID = :ID")
    List<Order> findByCustomerID(Long ID);

    @Query("SELECT o FROM Order o JOIN FETCH o.orderProducts")
    List<Order> findAllOrdersWithProducts();

    @Query("SELECT o FROM Order o JOIN FETCH o.orderProducts WHERE o.customer.ID = :ID")
    List<Order> findAllOrdersWithProductsByCustomerID(Long ID);

    @Query("SELECT o FROM Order o WHERE o.customer.email = :email")
    List<Order> findByCustomerEmail(String email);

}

