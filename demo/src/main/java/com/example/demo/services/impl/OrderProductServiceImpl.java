package com.example.demo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Customer;
import com.example.demo.models.OrderProduct;
import com.example.demo.repositories.OrderProductRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.services.OrderProductService;

@Service
public class OrderProductServiceImpl implements OrderProductService{
    
    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderRepository orderRepository;
    
    public OrderProductServiceImpl(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    public OrderProduct createOrderProduct(OrderProduct orderProduct) {

         java.util.Optional<OrderProduct> orderProductWithExistingOrder = orderProductRepository.findAll()
        .stream()
        .filter(x -> x.getOrder().getOrderID().equals(orderProduct.getOrder().getOrderID()))
        .findFirst();

        orderProductWithExistingOrder.ifPresent(c -> orderProduct.setOrder(c.getOrder()));

        return orderProductRepository.save(orderProduct);

    }

    public OrderProduct getOrderProduct(Long id) {
        return orderProductRepository.findById(id).orElse(null);
    }

   

    public OrderProduct updateOrderProduct(Long id, OrderProduct orderProduct) {
        OrderProduct existingOrderProduct = orderProductRepository.findById(id).orElse(null);
        if (existingOrderProduct != null) {
            existingOrderProduct.setOrder(orderProduct.getOrder());
            existingOrderProduct.setProduct(orderProduct.getProduct());
            existingOrderProduct.setQuantity(orderProduct.getQuantity());
            return orderProductRepository.save(existingOrderProduct);
        }
        return null;
    }

    public boolean deleteOrderProduct(Long id) {
        OrderProduct existingOrderProduct = orderProductRepository.findById(id).orElse(null);
        if (existingOrderProduct != null) {
            orderProductRepository.delete(existingOrderProduct);
            return true;
        }
        return false;
    }
 
    public List<OrderProduct> findByOrderId(Long orderID) {
        return orderProductRepository.findByOrderId(orderID);
    }

    public List<OrderProduct> getAllOrderProducts() {
        return orderProductRepository.findAll();
    }

    public List<OrderProduct> getOrderProductsByOrderId(Long orderID) {
        return orderProductRepository.findByOrderId(orderID);
    }



}
