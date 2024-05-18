package com.example.demo.services.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Customer;
import com.example.demo.models.Order;
import com.example.demo.models.OrderProduct;
import com.example.demo.models.Product;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.OrderProductRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.OrderService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository, OrderProductRepository orderProductRepository, ProductRepository productRepository, ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderProductRepository = orderProductRepository;
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }


    public List<String> allPreviousOrders(Long customerID) throws JsonProcessingException {
        List<String> orderList = new ArrayList<>();
        List<Order> orders = orderRepository.findAllOrdersWithProductsByCustomerID(customerID);
        
        for (Order order : orders) {
            Long orderId = order.getOrderID();
            
            for (OrderProduct orderProduct : order.getOrderProducts()) {
                String orderProductJson = objectMapper.writeValueAsString(orderProduct);
                orderList.add(orderProductJson);
            }
        }
        
        return orderList;
    }
    

     public void printAllOrdersToCSV(String file) {
        String filePath = System.getProperty("user.dir") + File.separator + file;
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("Order ID,Product ID,Product Name\n");


            List<Order> orders = orderRepository.findAllOrdersWithProducts();
            for (Order order : orders) {
                Long orderId = order.getOrderID();
               
                for (OrderProduct orderProduct : order.getOrderProducts()) {
                    Long productId = orderProduct.getProduct().getProductID();
                    String productName = orderProduct.getProduct().getProductName();

                    writer.append(String.format("%d,%d,%s\n", orderId, productId, productName));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Transactional
    public Order createOrderForCustomer(Long customerId, Order order) {
        // Check if customer exists
        java.util.Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            order.setCustomer(customer);
            return orderRepository.save(order);
        } else {
            // Customer does not exist, handle as required (e.g., throw exception, return null)
            return null;
        }

    }


    // public String exportDataToJson (Long customerID) {
       

    //     List<Order> orders = orderRepository.findByCustomerID(customerID);
    //     List<OrderProduct> orderProducts = new ArrayList<>();

    //     for (Order order : orders) {
    //         orderProducts = orderProductRepository.findByOrderId(order.getOrderID());

    //         // System.out.println("Order ID: "+order.getOrderID());
    //         // for(OrderProduct orderProduct: orderProducts){
                
    //         // text=" product name: "+orderProduct.getProduct().getProductName() + " quantity: "+orderProduct.getQuantity()
    //         // + " price: "+orderProduct.getPrice()+"\n";
            

            
    //     }
    //     try {
    //         return objectMapper.writeValueAsString(orderProducts);
    //     } catch (JsonProcessingException e) {

    //         e.printStackTrace();
    //         return null; 
    //     }
    
    // }

    public List<Order> findByCustomerID(Long customerID) {
        return orderRepository.findByCustomerID(customerID);
    }

    public List<Order> getOrdersByCustomer(Long customerID) {
        return orderRepository.findByCustomerID(customerID);
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order updateOrder(Long id, Order order) {
        Order existingOrder = orderRepository.findById(id).orElse(null);
        if (existingOrder != null) {
            existingOrder.setCustomer(order.getCustomer());
            //existingOrder.setProducts(order.getProducts());
            return orderRepository.save(existingOrder);
        }
        return null;
    }
}
