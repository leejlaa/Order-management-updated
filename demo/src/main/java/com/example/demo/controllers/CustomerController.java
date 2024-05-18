package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.models.Customer;
import com.example.demo.models.Order;
import com.example.demo.models.OrderProduct;
import com.example.demo.services.impl.CustomerServiceImpl;
import com.example.demo.services.impl.OrderProductServiceImpl;
import com.example.demo.services.impl.OrderServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerServiceImpl customerService;
    private final OrderServiceImpl orderService;
    private final OrderProductServiceImpl orderProductService;
    private final ObjectMapper objectMapper;

    public CustomerController(CustomerServiceImpl customerService, OrderServiceImpl orderService, OrderProductServiceImpl orderProductService) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.orderProductService = orderProductService;
        this.objectMapper = new ObjectMapper();
    }

    @PostMapping("/{adminID}")
    public Customer createCustomer(@PathVariable Long adminID, @RequestBody Customer customer) {
        return customerService.createCustomer(adminID,customer);
    }

    @GetMapping
    public List<CustomerDTO> getCustomers() {
        List<Customer> customers =  customerService.getCustomers();
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setUserName(customer.getUserName());
            customerDTO.setID(customer.getID());
            customerDTO.setEmail(customer.getEmail());
            customerDTO.setRole(customer.getRole());
            customerDTOs.add(customerDTO);
        }
        return customerDTOs;
    }

    @GetMapping("/export/{customerID}")
    public String exportDataToJson (Long customerID) throws JsonProcessingException {
       

        List<Order> orders = orderService.findByCustomerID(customerID);
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (Order order : orders) {
            orderProducts = orderProductService.findByOrderId(order.getOrderID());

            // System.out.println("Order ID: "+order.getOrderID());
            // for(OrderProduct orderProduct: orderProducts){
            //     System.out.println(
            // " product name: "+orderProduct.getProduct().getProductName() + " quantity: "+orderProduct.getQuantity()
            // + " price: "+orderProduct.getPrice());
            // }

            
        }
        return objectMapper.writeValueAsString(orderProducts);
    
    }

    
    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable Long id) {
        Customer customer =  customerService.getCustomer(id);
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setUserName(customer.getUserName());
        customerDTO.setID(customer.getID());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setRole(customer.getRole());
        return customerDTO;
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        Customer customerNew =  customerService.updateCustomer(id, customer);
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setUserName(customerNew.getUserName());
        customerDTO.setID(customerNew.getID());
        customerDTO.setEmail(customerNew.getEmail());
        customerDTO.setRole(customerNew.getRole());

        return customerDTO;
    }
    
    @DeleteMapping("/{userName}")
    public boolean deleteCustomer(@PathVariable String userName) {
        return customerService.deleteCustomer(userName);
    }
    
    
    
}
