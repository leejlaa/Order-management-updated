package com.example.demo.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdminDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.models.Order;
import com.example.demo.models.OrderProduct;
import com.example.demo.services.impl.OrderServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderServiceImpl orderService;


   @PostMapping("/{customerId}")
public void createOrder(@PathVariable Long customerId, @RequestBody Order orderRequest ){

    orderService.createOrderForCustomer(customerId, orderRequest);
}


   

    @GetMapping("/{id}")
    public String getOrder(@PathVariable Long id) {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = ""; 
       
       List<Order> orders = orderService.findByCustomerID(id);

       double total = 0;
       for(Order o : orders){

        json += "Order ID: "+o.getOrderID()+"\n";
        

        for (OrderProduct orderProduct : o.getOrderProducts()) {

            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductName(orderProduct.getProduct().getProductName());
            productDTO.setProductID(orderProduct.getProduct().getProductID());
            productDTO.setPrice(orderProduct.getProduct().getPrice());
            productDTO.setQuantity(orderProduct.getQuantity());
            productDTO.setReleaseDate(orderProduct.getProduct().getReleaseDate());
            productDTO.setAvailabilityDate(orderProduct.getProduct().getAvailabilityDate());
            total += orderProduct.getProduct().getPrice()* orderProduct.getQuantity();
            try {
                json += objectMapper.writeValueAsString(productDTO)+ "\n";
            }catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                }
            }
            json += "Total: "+total+"\n";
            total = 0;
        }

        
      

        // adminDTO = new AdminDTO();
        // adminDTO.setUserName(admin.getUserName());
        // adminDTO.setID(admin.getID());
        // adminDTO.setEmail(admin.getEmail());
        // adminDTO.setRole(admin.getRole());
       
        // try {
        //     json = objectMapper.writeValueAsString(adminDTO);
        //     users += json+"\n";
        // } catch (JsonProcessingException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }

       
       return json;
       
    }

    // @GetMapping("/{customerId}")
    // public Order getOrdersByCustomer(@PathVariable Long customerId) {
    //     return orderService.getOrderByCustomer(customerId);
    // }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    @PostMapping
    public void allOrders(@RequestBody String path) {
        orderService.printAllOrdersToCSV(path);
    }

    
}
