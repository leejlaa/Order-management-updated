package com.example.demo.services;

import java.util.List;

import com.example.demo.dto.PlaceOrderRequest;
import com.example.demo.models.Order;

public interface OrderService {

    public Order createOrderForCustomer(Long customerID, PlaceOrderRequest order) throws Exception;
    public List<Order> findByCustomerID(Long customerID);
    public List<Order> findByCustomerEmail(String email);
    public List<Order> getOrdersByCustomer(Long customerID);
    public Order getOrder(Long id);
    public Order updateOrder(Long id, Order order);
    
}
