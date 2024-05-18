package com.example.demo.services;

import java.util.List;

import com.example.demo.models.OrderProduct;

public interface OrderProductService {
    public OrderProduct createOrderProduct(OrderProduct orderProduct);
    public OrderProduct getOrderProduct(Long id);
    public OrderProduct updateOrderProduct(Long id, OrderProduct orderProduct);
    public boolean deleteOrderProduct(Long id);
    public List<OrderProduct> findByOrderId(Long orderID);
    public List<OrderProduct> getAllOrderProducts();
    public List<OrderProduct> getOrderProductsByOrderId(Long orderID);

}
