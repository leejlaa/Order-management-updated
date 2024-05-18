package com.example.demo.services;

import java.util.List;

import com.example.demo.models.Order;

public interface OrderService {

    public Order createOrderForCustomer(Long customerID, Order order);
    public List<Order> findByCustomerID(Long customerID);
    public List<Order> getOrdersByCustomer(Long customerID);
    public Order getOrder(Long id);
    public Order updateOrder(Long id, Order order);
    
}
